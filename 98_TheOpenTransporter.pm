#!/usr/bin/perl

#########################################################################################################################################################
#
#  Copyright notice
#
#  (c) 2012 HS-Harz
#  Copyright: Marcus Viererbe, Oliver Jathe
#  All rights reserved
#
#  This script free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#
#  The GNU General Public License can be found at
#  http://www.gnu.org/copyleft/gpl.html.
#
#  This script is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  This copyright notice MUST APPEAR in all copies of the script!
#  Thanks for all inspirations.	
#
#########################################################################################################################################################

#For questions contact us:
#	OJathe@TheOpenTransporter.de
#	MarcusViererbe@TheOpenTransporter.de

# Programmierrichtlinien
#########################################################################################################################################################
# - Funktionen: TheOpenTransporter_Funktionsname
# - Variablen:	- Zusammengesetzte  Variablen erster Buchstabe immer klein und zusammen ohne underline, dannach erster Buchstabe groß
#		- einzelne Variablen klein
# - Die Einrückungstiefe beträgt 1 Tabulatorschritt
# - Vor einem Semikolon oder Komma wird kein Whitespace (Leerzeichen, Tabulator, Zeilenwechsel) gesetzt.
# - Nach einem Semikolon oder Komma steht immer ein Leerzeichen oder Zeilenwechsel
#
# Ausdrücke
###########
# - Nach einem Ausdruck erfolgt die Klammer in der selben Zeile mit einem Leerzeichen zur Trennung
# - Die geschweiften Klammern werden zur Übersicht in eine extra Zeile geschrieben
# - Vor und nach Operatoren wird ein Leerzeichen gesetzt
#
# Beispiel:
# if (a < b)
# {
#         funktion();
# }
#
# Variablen
###########
# - Variablennamen fangen grundsätzlich klein an
# - Sollten Variablennamen aus mehreren Wörtern bestehen, fängt jede weitere Zusammensetzung mit einem großen Buchstaben an
# - Der Unterstrich „_“ ist in Variablennamen verboten
# - Variablennamen entsprechen ihrem Verwendungszweck
# - Es werden englische Namen verwendet
#
# Beispiel:
# my $allDevices;
#
# Methoden
##########
# - Methodennamen fangen grundsätzlich groß an
# - Methodennamen fangen grundsätzlich mit dem Modulbezeichner an "TheOpenTransporter" gefolgt von einem "_" (Unterstrich)
# - Sollten Methodennamen aus mehreren Wörtern bestehen, fängt jede weitere Zusammensetzung mit einem großen Buchstaben an
# - Methodennamen entsprechen ihrem Verwendungszweck.
# - Es werden englische Namen verwendet.
#
# Beispiel: 
# function TheOpenTransporter_GetAllDevices()
# {
#        ...
# }
#
# Kommentare
############
# - Kommentare sind grundsätzlich in deutsch zu halten
# - Alle Kommentare beginnen mit einem "#" Zeichen
#
#########################################################################################################################################################

package main;

use strict;
use warnings;
use IO::Socket;
use IO::File;
use JSON;

# ist notwendig, da TheOpenTransporter_Define vor TheOpenTransporter_Attr geladen wird, und der Interpreter eine prototype Warnung auswirft
# (forward declaration)
#Forward declarations
#########################################################################################################################################################
sub TheOpenTransporter_Attr(@);
sub TheOpenTransporter_FntGetDevice($$);
sub TheOpenTransporter_FntGetAllDevices($$);
sub TheOpenTransporter_FntSetDevice($$);
sub TheOpenTransporter_FntNewDevice($$);
sub TheOpenTransporter_FntDelDevice($$);
sub TheOpenTransporter_FntGetNonDefinedDevices($$);
sub TheOpenTransporter_FntNewStructure($$);
sub TheOpenTransporter_FntDeleteStructure($$);
sub TheOpenTransporter_FntAddDeviceInStructure($$);
sub TheOpenTransporter_FntDeleteDeviceInStructure($$);
sub TheOpenTransporter_FntIsLogFileAvailable($$);
sub TheOpenTransporter_FntGetLogFromDevice($$);
sub TheOpenTransporter_FntGetLogFromDeviceInPeriod($$);
sub TheOpenTransporter_FntGetAllScripts($$);
sub TheOpenTransporter_FntGetScript($$);
sub TheOpenTransporter_FntAddScript($$);
sub TheOpenTransporter_FntDeleteScript($$);
sub TheOpenTransporter_FntGetVersion($$);
sub TheOpenTransporter_PackageFunctionExists($$);
sub TheOpenTransporter_Execute($$);

#########################################################################################################################################################
# Die Methode Initialize wird beim Laden des Moduls ausgeführt, hier werden die vorhandenen Schnittstellen zu fhem
# definiert. ReadFn ist die Methode die Aufgerufen wird, wenn das übergebene FileHandel ein IO Vorgang vollzieht.
# DefFn und UndefFn dienen zum laden und entladen des Moduls in fhem.

sub TheOpenTransporter_Initialize($)
{
	my ($hash) = @_;
	$hash->{ReadFn}  = "TheOpenTransporter_Read";
	$hash->{DefFn}   = "TheOpenTransporter_Define";
	$hash->{UndefFn} = "TheOpenTransporter_Undef";
	$hash->{AttrFn}  = "TheOpenTransporter_Attr";
	$hash->{NotifyFn}= "TheOpenTransporter_NotifyFn";
	$hash->{AttrList}= "loglevel:0,1,2,3,4,5,6 HTTPS SSL_KEY SSL_CERT RequestHeaderLineLimit";
}

#########################################################################################################################################################

sub TheOpenTransporter_Log($$$)
{
	my ($hash, $logLevel, $msg) = @_;
	Log GetLogLevel($hash->{NAME}, $logLevel), $hash->{NAME}.": ".$msg;
}

#########################################################################################################################################################
# Die Methode Define wird bei einer Definition des Moduls in fhem.pl aufgerufen.
# Dabei werden Syntax der Angegebenen Parameter überprüft.
# Anschließend wird ein passiver Socket erzeugt, der auf dem angegebene Port auf eingehende Verbindungen wartet.
# Es mmuss das FileHandles des passiven Sockets auf die lokale Hashtable $hash->{FD} zugewiesen werden, dies
# dient dazu, um lese/schreib Vorgänge zu erkenen, da alle anderen Funktionen immer von der fhem.pl aufgerufen werden.
# Um überaupt in die Liste der aufzurufenden Module zu gelangen muss sich das Modul in die Liste "selectlist" eintragen.
# Dies geschiet über den Namen des Moduls, zugewiesen wird die gesammte Hash-Table.
# Wichtig ist das Zurückgeben eines "undef" Returnwertes, da ansonsten das Modul nicht Defined wird!!!!!!!!!!!!!!!!

sub TheOpenTransporter_Define($$)
{
	my ($hash, $def) = @_;
	# Check if define is OK
	if ($def !~ /^(\w+)\s+TheOpenTransporter\s+(\d+)\s*((?:IPV6\s*|HTTPS\s*|SSL_KEY\s*.+\s*|SSL_CERT\s*.+\s*:){0,4})$/)
	{
		return "wrong syntax: define <name> TheOpenTransporter <Port> [Optional: IPV6] [Optional: HTTPS] ".
			"[Optional: SSL_KEY </path/server-key.pem> SSL_CERT </path/server-cert.pem>]";
	}
	# if exists optional params, rewrite to def
	$def = $3 || "";
	# festlegen der TOT Version
	$hash->{VERSION} = "1.1.0";
	# festlegen des Namen
	$hash->{NAME} = $1;
	# festlegen des Port's
  	$hash->{PORT} = $2;
	# festlegen des RequestHeaderLineLimits
	$hash->{RequestHeaderLineLimit} = 35;
	# anlegen der Liste mit den nicht definierten Devices
	my @undefList;
	$hash->{undefList} = \@undefList;
	# IPV6 support => install: apt-get install libio-socket-inet6-perl
	if ($def =~ /\s+IPV6/)
	{
		eval "require IO::Socket::INET6; use Socket6;";
		if($@)
		{
			TheOpenTransporter_Log($hash, 1, $@);
			TheOpenTransporter_Log($hash, 1, "Can't load INET6, falling back to IPV4");
		}
		else
		{
			$hash->{IPV6} = 1;
			TheOpenTransporter_Log($hash, 4, "Load INET6");
		}
	}
	# HTTPS (SSL) support
	if ($def =~ /\s+HTTPS/)
	{
		TheOpenTransporter_Attr("set", $hash->{NAME}, "HTTPS");
	}
	# KEY und CERT Ablage an einem anderen Speicherort
	if ($def =~ /\s+SSL_KEY\s+(.+)\s+SSL_CERT\s+(.+)/)
	{
		my ( $sslKey, $sslCert ) = ( $1, $2 );
		$sslKey =~ s/\s*$//g;
		$sslCert =~ s/\s*$//g;
		# prüft, ob die SSL_KEY Datei lesbar ist (existent und benutzbar)
		TheOpenTransporter_Attr("set", $hash->{NAME}, "SSL_KEY", $sslKey);
		# prüft, ob die SSL_CERT Datei lesbar ist (existent und benutzbar)
		TheOpenTransporter_Attr("set", $hash->{NAME}, "SSL_CERT", $sslCert);
	}
	# Optionen für den Server (Listener)
	my @opts = (	
		Domain		=> $hash->{IPV6} ? AF_INET6() : AF_UNSPEC,
		LocalHost	=> undef, 
		LocalPort	=> $hash->{PORT}, 
		Proto 		=> "tcp", 
		Listen		=> 1, 
		Reuse		=> 1
		);
	# Status von TOT auf Init stellen
	$hash->{STATE} = "Initialized";
	# Server Socket (listener) wird angelegt und versucht zu öffnen
	$hash->{SERVERSOCKET} = $hash->{IPV6} ? IO::Socket::INET6->new(@opts) : IO::Socket::INET->new(@opts);
	# tritt ein, wenn das öfnnen des Server Sockets fehlgeschlagen ist
	if(!$hash->{SERVERSOCKET})
	{
    	return "Can't open server port at $hash->{PORT}: $!";
  	}
	# File Descriptor (Handle) wird abgefragt und in Moduleigene Hashtable gespeichert
 	$hash->{FD} = $hash->{SERVERSOCKET}->fileno();
	# schreibt eigenes Modul in selectlist, um zyklische Abfragen auf Verbindungsanforderung zu erreichen
  	$selectlist{$hash->{NAME}} = $hash;
  	Log 2, "$hash->{NAME} on port $hash->{PORT} opened! (Version: $hash->{VERSION})";
  	return undef;
}

#########################################################################################################################################################
# TheOpenTransporter_Read wird in der Main Schleife von dem Prozess fhem.pl ausgeführt.
# Dazu muss sich das Modul in eine einer Hash-Table names "selectlist" befinden, Bedingung ist, dass die lokale
# Hash-Table des Moduls in der Define Phase in dieser eingetragen wird, hier: $selectlist{$name} = $hash;
# Im weiteren muss der eigenen Hash-Table ein Handle überegeben werden, hier: $hash->{FD} = Handle, um bei
# Schreieb/Lesevorgängen die Methode TheOpenTransporter_Read aufzurufen.
# Auch muss im init Abschnitt der Hash-Table der Funktionsname übergeben werden.

sub TheOpenTransporter_Read($)
{
	# eigene Hash-Table wird vom Übergabeparameter in $hash gespeichert
	my ($hash) = @_;
	# neue eingehende Verbindung, bei eintreten diese Falls, sind wir noch im Modul des Serversockets, andernfalls sind wir im Clientmodul!!!!!!!!!!
	if ($hash->{SERVERSOCKET})
	{
		# Methode accept() blockiert den passiven ServerSocket, bis eine eingehende Verbindung etabliert wird
		my $newClient = $hash->{SERVERSOCKET}->accept();
		if(!$newClient)
		{
			TheOpenTransporter_Log($hash, 1, "Accept failed for HTTP port (".$hash->{NAME}.": $!)");
			return undef;
		}
		my %newClientHash = (	"NR" => $devcount++,
					"NAME" => $hash->{NAME}.":".$newClient->peerhost().":".$newClient->peerport(),
					"FD" => $newClient->fileno(),
					"CLIENT" =>  $newClient,
					"TYPE" => "TheOpenTransporter",
					"STATE" => "Connected",
					"RequestHeaderLineLimit" => $hash->{RequestHeaderLineLimit},
					"TEMPORARY" => 1,
					"VERSION" => $hash->{VERSION},
					);
		$attr{$newClientHash{NAME}}{room} = "hidden";
		if (defined($attr{$hash->{NAME}}{loglevel}))
		{
			$attr{$newClientHash{NAME}}{loglevel} = $attr{$hash->{NAME}}{loglevel};
		}
		$defs{$newClientHash{NAME}} = \%newClientHash;
		$selectlist{$newClientHash{NAME}} = \%newClientHash;
		# Log $attr{$hash->{NAME}}{loglevel}, "SSL Hash Test: Orig: ".$hash->{SSl}." selbst geholt: ".$defs{"TheOpenTransporter0"}{SSL}; ???	
		# Wenn SSl aktiv ($defs{"TheOpenTransporter0"}{SSL} == 1), dann neue session mit ssl beginnen
		if ($defs{$hash->{NAME}}{SSL})
		{
			# installed with cpan -i IO::Socket::SSL or apt-get install libio-socket-ssl-perl
			# mkdir certs
			# cd certs
			# openssl req -new -x509 -nodes -out server-cert.pem -days 3650 -keyout server-key.pem 
			# nicht vergessen zertifikate in /usr/bin/certs zu packen sonst wird das nix ;-)
			my $ret = IO::Socket::SSL->start_SSL(
				$newClient, SSL_key_file => $hash->{SSL_KEY}, 
				SSL_cert_file => $hash->{SSL_CERT}, 
				SSL_server => 1
				);
			if(!$ret && $! ne "Socket is not connected")
			{
      				TheOpenTransporter_Log($hash, 1, "HTTPS Enabled: $!");
			}
		}
		return undef;
	}
	# liest erste Zeile der Anfrage	
	my $request = $hash->{CLIENT}->getline();
	# prüft, ob Verbindung noch existent
	if(!defined($request))
	{
		CommandDelete(undef, $hash->{NAME});
		return undef;
	}
	
	# Art des Services wird bestimt
	if ($request !~ /^([A-Z]+) (\/.*) HTTP\/(\d\.\d)\r\n$/)
	{
		CommandDelete(undef, $hash->{NAME});
		return undef;
	}
	
	my %service = (
		"GET"				=> "TheOpenTransporter_ProzessGETResponse",
		"POST"				=> "TheOpenTransporter_ProzessPOSTResponse",
		"OPTIONS"			=> "TheOpenTransporter_ProzessOPTIONSResponse",
		"NOT_IMPLEMENTED"	=> "TheOpenTransporter_ProzessNotImplementedResponse"
		);
	
	# Service Routine wird dank der Hash-Table aufgerufen, dabei werden Socket und Parameter übergeben
	TheOpenTransporter_CallFnt($service{exists($service{$1}) ? $1 : "NOT_IMPLEMENTED"}, $hash, $2, $3);
	
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_Undef($$)
{
	my ($hash, $arg) = @_;
	# überprüfung, ob es sich um ein Klon (Connection Modul) handelt (Verbindung zum Client)
	if(defined($hash->{CLIENT}))
	{
		# schließt die Client Verbindung
    	close($hash->{CLIENT});
		TheOpenTransporter_Log($hash, 2, "Connection closed!");
		# löscht die zyklische Abfrage evt. anliegender Daten des Klon (Connection Modul)
    	delete($selectlist{$hash->{NAME}});
  	}
	# überprüft, ob es sich um das Server Modul handelt
  	if(defined($hash->{SERVERSOCKET}))
	{
		# schließt den Listener
    	close($hash->{SERVERSOCKET});
		TheOpenTransporter_Log($hash, 2, "Connection closed!");
		# löscht zyklische Anbfrage auf anliegende Client-Verbindungsanforderungen
    	delete($selectlist{$hash->{NAME}});
  	}
  	return undef;
}

#########################################################################################################################################################
# Die Methode TheOpenTransporter_Attr wird von der fhem.pl aufgerufen, um Attribute in dem eigenen Modul zu setzen.

sub TheOpenTransporter_Attr(@)
{
	my @a = @_;
	my $hash = $defs{$a[1]};
	if($a[0] eq "set" && $a[2] eq "HTTPS")
	{
		eval "require IO::Socket::SSL";
		if($@)
		{
			TheOpenTransporter_Log($hash, 1, $@);
			TheOpenTransporter_Log($hash, 1, "Can't load IO::Socket::SSL, falling back to HTTP");
		}
		else
		{
			# prüft, ob die SSL_KEY Datei lesbar ist (existent und benutzbar)
			TheOpenTransporter_Attr("set", $hash->{NAME}, "SSL_KEY", 
				(defined($hash->{SSL_KEY})) ? $hash->{SSL_KEY} : "/usr/bin/certs/server-key.pem");
			# prüft, ob die SSL_CERT Datei lesbar ist (existent und benutzbar)
			TheOpenTransporter_Attr("set", $hash->{NAME}, "SSL_CERT", 
				(defined($hash->{SSL_CERT})) ? $hash->{SSL_CERT} : "/usr/bin/certs/server-cert.pem");
		}
		return undef;
	}
	if($a[0] eq "set" && $a[2] eq "SSL_KEY")
	{
		# prüft, ob die SSL_KEY Datei lesbar ist (existent und benutzbar)
		if (-r $a[3])
		{
			$hash->{SSL_KEY} = $a[3];
			$hash->{SSL} = 1;
			TheOpenTransporter_Log($hash, 4, "SSL_CERT = ".$a[3]);
		}
		else
		{
			TheOpenTransporter_Log($hash, 2, "Can't open SSL_KEY: ".$a[3].": Datei oder Verzeichnis nicht gefunden!");
			$hash->{SSL_KEY} = undef;
			$hash->{SSL} = 0;
			TheOpenTransporter_Log($hash, 2, "Cant't enable SSL!");
		}
		return undef;
	}
	if($a[0] eq "set" && $a[2] eq "SSL_CERT")
	{
		# prüft, ob die SSL_CERT Datei lesbar ist (existent und benutzbar)
		if (-r $a[3])
		{
			$hash->{SSL_CERT} = $a[3];
			$hash->{SSL} = 1;
			TheOpenTransporter_Log($hash, 4, "SSL_CERT = ".$a[3]); 
		}
		else
		{
			TheOpenTransporter_Log($hash, 2, "Can't open SSL_CERT: ".$a[3].": Datei oder Verzeichnis nicht gefunden!");
			$hash->{SSL_CERT} = undef;
			$hash->{SSL} = 0;
			TheOpenTransporter_Log($hash, 2, "Cant't enable SSL!");
		}
		return undef;
	}
	if($a[0] eq "set" && $a[2] eq "RequestHeaderLineLimit")
	{
		$hash->{RequestHeaderLineLimit} = 35;
		TheOpenTransporter_Log($hash, 4, "RequestHeaderLineLimit = ".$a[3]);
		return undef;
	}
	return undef;
}

#########################################################################################################################################################
# Auszug aus $dev->{CHANGED}[$i] = UNDEFINED FHT_3a0d FHT 3a0d
# Auszug aus $dev->{CHANGED}[$i] = UNDEFINED CUL_EM_1 CUL_EM 1

sub TheOpenTransporter_NotifyFn($$)
{
	my ($hash, $dev) = @_;
	# abfrage, ob Hauptmodul, da je nach Anzahl der Verbindungen jedes Modul dies mitgriegt
	if (!exists($hash->{SERVERSOCKET}))
	{
		return undef;
	}
	for (my $i = 0; $i < int(@{$dev->{CHANGED}}); $i++)
	{
		if (!($dev->{CHANGED}[$i] =~ "UNDEFINED"))
		{
			next;
		}
		my $isInList = 0;
		for (my $j = 0; $j < @{$hash->{undefList}}; $j++)
		{
			if ($hash->{undefList}[$j] eq $dev->{CHANGED}[$i])
			{
				$isInList = 1;
				last;
			}
		}
		if ($isInList == 0)
		{
			push @{$hash->{undefList}}, $dev->{CHANGED}[$i];
		}
	}
}

#########################################################################################################################################################

sub TheOpenTransporter_ProzessOPTIONSResponse($$)
{
	my ($hash, @subRequest) = @_;
	my $client = $hash->{CLIENT};
	print $client	"HTTP/1.1 200 OK\r\n",
			"Date: ".localtime(time()+900)." GMT\r\n",
			"Server: TheOpenTransporter/1.0\r\n",
			"Content-Length: 0\r\n",
			"Allow: OPTIONS, GET, POST\r\n",
			"Connection: close\r\n",
			"\r\n";
	TheOpenTransporter_Log($hash, 5, "OPTIONS Response");
	CommandDelete(undef, $hash->{NAME});
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_ProzessNotImplementedResponse($$)
{
	my ($hash, @subRequest) = @_;
	my $client = $hash->{CLIENT};
	my $result = "<html>\r\n<head>\r\n</head>\r\n<body>\r\n<h1>501 Not Implemented</h1>\r\n</body>\r\n</html>";
	print $client	"HTTP/1.1 501 Not Implemented\r\n",
			"Date: ".localtime(time()+900)." GMT\r\n",
			"Server: TheOpenTransporter/1.0\r\n",
			"Content-Length: ".length($result)."\r\n",
			"Content-Type: text/html; ISO-8859-1\r\n",
			"Connection: close\r\n",
			"\r\n",
			$result;
	TheOpenTransporter_Log($hash, 5, "NOT_IMPLEMENTET Response");
	CommandDelete(undef, $hash->{NAME});
	return undef;
}

#########################################################################################################################################################
# Diese Procedure verarbeitet eine HTTP Get Anfrage eines Clients, und sendet ein HTTP Header und Body, 
# je nach erfolg der angeforderten Daten.

sub TheOpenTransporter_ProzessGETResponse($$$)
{
	my ($hash, $function, $httpVersion) = @_;
	if ($httpVersion ne "1.0" && $httpVersion ne "1.1")
	{
		# 505 HTTP Version Not Supported, da nur Version 1.0 und 1.1 unterstützt wird
		$hash->{CLIENT}->send("HTTP/1.0 505 HTTP Version Not Supported\r\n\r\n");
		TheOpenTransporter_Log($hash, 5, "HTTP Version($httpVersion) not supported.");
		CommandDelete(undef, $hash->{NAME});
		return undef;
	}
	# parsen der URI
	if ($function !~ m/^\/(\w*)\??(.*)$/)
	{
		#400 - Bad Request, da nur HTTP unterstützt wird
		$hash->{CLIENT}->send("HTTP/1.0 400 Bad Request\r\n\r\n");
		TheOpenTransporter_Log($hash, 5, "Invalid URL \"$function\"");
		CommandDelete(undef, $hash->{NAME});
		return undef;
	}
	my %dataHash = TheOpenTransporter_DecodeRawData($hash, "application/x-www-form-urlencoded", $2 || "");
	my %httpHeader  = TheOpenTransporter_ReadHeaderLines($hash);
	my %fntHash = ( "param" => \%dataHash );
	TheOpenTransporter_ProzessInfo($1, $hash, \%fntHash);
	my $accept = $httpHeader{"Accept"};
	if (!$accept)
	{
		$accept = "text/html";
		TheOpenTransporter_Log($hash, 5, "GET - Can't find Accept Header, fallback to \"text/html\".");
		# 415 Unsupported media type (Nicht unterstützter Medientyp)
	}
	my $connection = $httpHeader{"Connection"};
	if (!$connection)
	{
		$connection = "Keep-alive";
	}
	# prüft auf existenz von ContentType oder legt diesen fest, hierbei wird auch eine
	# Überprüfung der Unterstützten Methoden durchgeführt
	if (!exists($fntHash{"ContentType"}) || $accept !~ $fntHash{"ContentType"})
	{
		$fntHash{"ContentType"} = "text/html";
	}
	TheOpenTransporter_Reply($hash, $httpVersion, $fntHash{"Content"}, $fntHash{"ContentType"}, $connection);
	return undef;
}

#########################################################################################################################################################
# Diese Procedure verarbeitet eine HTTP POST Anfrage eines Clients, und sendet ein HTTP Return Code
# und je nach erfolg die angeforderten Daten.

sub TheOpenTransporter_ProzessPOSTResponse($$)
{
	my ( $hash, $function, $httpVersion ) = @_;
	if ($httpVersion ne "1.0" && $httpVersion ne "1.1")
	{
		# 505 HTTP Version Not Supported, da nur Version 1.0 und 1.1 unterstützt wird
		$hash->{CLIENT}->send("HTTP/1.0 505 HTTP Version Not Supported\r\n\r\n");
		TheOpenTransporter_Log($hash, 5, "HTTP Version($httpVersion) not supported.");
		CommandDelete(undef, $hash->{NAME});
		return undef;
	}
	# parsen der POST URL
	if ($function !~ m/^\/(.*)$/)
	{
		#400 - Bad Request, da nur HTTP unterstützt wird
		$hash->{CLIENT}->send("HTTP/1.0 400 Bad Request\r\n\r\n");
		TheOpenTransporter_Log($hash, 5, "Invalid URL \"$function\"");
		CommandDelete(undef, $hash->{NAME});
		return undef;
	}
	$function = $1;
	my %httpHeader  = TheOpenTransporter_ReadHeaderLines($hash);
	# Content-Length kann auch 0 sein, daher auf defined prüfen
	if (!defined($httpHeader{"Content-Length"}))
	{
		# 411 Length Required- Die Anfrage kann ohne ein „Content-Length“-Header-Feld nicht bearbeitet werden.
		$hash->{CLIENT}->send("HTTP/1.0 411 Length Required\r\n\r\n");
		TheOpenTransporter_Log($hash, 5, "411 Length Required for Request \"$function\".");
		CommandDelete(undef, $hash->{NAME});
		return undef;
	}
	my ( $bytesToRead, %fntHash ) = ( int($httpHeader{"Content-Length"}), ( "param" => {} ) );
	# Decodieren des Eingangsformat in das Allgemeine Zielformat
	if ($bytesToRead > 0 && $httpHeader{"Content-Type"} && 
		(my %param = TheOpenTransporter_DecodeRawData($hash, $httpHeader{"Content-Type"}, 
		TheOpenTransporter_SocketRead($hash, $bytesToRead, 2, 0.1))))
	{
		$fntHash{"param"} = \%param;
	}
	TheOpenTransporter_ProzessInfo($function, $hash, \%fntHash);	
	my $accept = $httpHeader{"Accept"};
	if (!$accept)
	{
		$accept = "text/html";
		TheOpenTransporter_Log($hash, 5, "POST - Can't find Accept Header, fallback to \"text/html\".");
		# 415 Unsupported media type (Nicht unterstützter Medientyp)
	}
	my $connection = $httpHeader{"Connection"};
	if (!$connection)
	{
		$connection = "Keep-alive";
	}
	# prüft auf existenz von ContentType oder legt diesen fest, hierbei wird auch eine
	# Überprüfung der Unterstützten Methoden durchgeführt
	if (!$fntHash{"ContentType"} || $accept !~ $fntHash{"ContentType"})
	{
		$fntHash{"ContentType"} = "text/html";
	}
	TheOpenTransporter_Reply($hash, $httpVersion, $fntHash{"Content"}, $fntHash{"ContentType"}, $connection);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_Reply($$$$$)
{
	my ( $hash, $httpVersion, $content, $contentType, $connection ) = @_;
	
	my $responseCodeLine = "200 OK";
	
	# prüft auf vorhandensein eines Content
	if (!defined($content))
	{
		$responseCodeLine = "404 Not Found";
		$contentType = "text/html";
		$content = '<html lang="de"><head></head><body><h1>404 Not Found</h1></body></html>';
	}
	
	# Antwort für HTTP/1.0
	if ($httpVersion eq "1.0")
	{
		$hash->{CLIENT}->send(
			"HTTP/$httpVersion $responseCodeLine\r\n".
			"Date: ".localtime(time()+900)." GMT\r\n".
			"Server: TheOpenTransporter/1.0\r\n".
			"Content-Length: ".length($content)."\r\n".
			"Content-Type: $contentType; ISO-8859-1\r\n".
			"\r\n".
			$content
			);
		CommandDelete(undef, $hash->{NAME});
		return;
	}
	
	# Antwort für HTTP/1.1
	if ($httpVersion eq "1.1")
	{
		$hash->{CLIENT}->send(
			"HTTP/$httpVersion $responseCodeLine\r\n".
			"Date: ".localtime(time()+900)." GMT\r\n".
			"Server: TheOpenTransporter/1.0\r\n".
			"Content-Length: ".length($content)."\r\n".
			"Content-Type: $contentType; ISO-8859-1\r\n".
			"Cache-Control:	no-store\r\n".
			"Connection: $connection\r\n".
			"\r\n".
			$content
			);
		if ($connection =~ "close")
		{
			CommandDelete(undef, $hash->{NAME});
		}
		return;
	}
}

#########################################################################################################################################################

sub TheOpenTransporter_ReadHeaderLines($)
{
	my ( $hash ) = @_;
	my %httpHeader;
	# auslesen der header-lines
	for(my $i = 1;;$i++)
	{
		# einlesen der requestLine
		my $requestLine = $hash->{CLIENT}->getline();
		# prüfen auf defined
		if (!defined($requestLine) || $requestLine eq "\r\n")
		{
			last;
		}
		# fügt bei Treffer die requestLine der Hashtable hinzu
		if ($requestLine =~ /^(.+): (.+)\r\n$/)
		{
			$httpHeader{$1} = $2;
		}
		if ($i == $hash->{RequestHeaderLineLimit})
		{
			TheOpenTransporter_Log($hash, 5, "RequestHeaderLineLimit of $i was reached.");
			last;
		}
	}
	return %httpHeader;
}

#########################################################################################################################################################

sub TheOpenTransporter_SocketRead($$$$)
{
    my ( $hash, $count, $timeout, $waitForReply ) = @_;
	if (!defined($timeout))
	{
		$timeout = 2;
	}
	if (!defined($waitForReply))
	{
		$waitForReply = 0.1;
	}
	my $data = "";
	if (defined($count))
	{
		while ($count > 0 && $timeout > 0)
		{
			my $receivedData;
			my $receivedBytes = $hash->{CLIENT}->read($receivedData, $count);
			
			if ($count > $receivedBytes)
			{
				select(undef, undef, undef, $waitForReply);
				$timeout -= $waitForReply;
			}
			# Wenn irgendwass empfangen wurde
			if ($receivedBytes > 0)
			{
				$data .= $receivedData;
				$count -= $receivedBytes;
			}
		}		
		return $count == 0 && $timeout > 0 ? $data : undef;
	}
	while (my $ret = $hash->{CLIENT}->read(1))
	{
		$data .= $ret;
	}
	return length($data) > 0 ? $data : undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_DecodeRawData($$$)
{
	my ($hash, $contentType, $rawData) = @_;
	if (!defined($rawData))
	{
		return undef;
	}
	if ($contentType =~ "application/x-www-form-urlencoded")
	{
		TheOpenTransporter_Log($hash, 5, "Content-Type: application/x-www-form-urlencoded");
		my %decodedHash;
		# splitet die Daten (key=value&key=value) jeweils als Datenpaar (key=value)
		my @pairOfValues = split("&", $rawData);
		for (my $i = 0; $i < @pairOfValues; $i++)
		{
			my @pair = split("=", $pairOfValues[$i]);
			$decodedHash{$pair[0]} = $pair[1];
			if (defined($decodedHash{$pair[0]}))
			{
				# ersetzen von allen URL Leerzeichen (%20) mit allgemeinen Leerzeichen
				$decodedHash{$pair[0]} =~ s/%20/ /g;
			}
		}
		return %decodedHash;
	}
	if ($contentType =~ "application/json")
	{
		TheOpenTransporter_Log($hash, 5, "Content-Type: application/json");
		#return %{JSON->new->allow_nonref->utf8->decode($rawData)};
		my %decodedHash;
		#%{JSON->new->allow_nonref->utf8->decode($rawData)};
		#$rawData = '{"NAME":"dasLetzte","TYPE":"notify","REGEXP":"Dimmer01","DEF":"// hier fehlt Code"}';
		$rawData = substr($rawData, 2, length($rawData)-4);
		my @preEncode = split('","', $rawData);
		for (my $i = 0; $i < @preEncode; $i++)
		{
			my @pair = split('":"', $preEncode[$i]);
			
			$decodedHash{$pair[0]} = $pair[1];
		}
		return %decodedHash;
	}
	TheOpenTransporter_Log($hash, 5, "Unbekannter Content-Type: ".$contentType);
	return undef;
}

#########################################################################################################################################################
# Die Methode ProzessInfo ruft die passende Funktion, wenn vorhanden, auf.
# Dies bewerkststelligt sie, indem eine Hash-Table "FntTable" auf die existenz der angegebenen Funktion durchsucht wird.
# Bei einem Treffer wird die entsprechende Funktion mit TheOpenTransporter_CallFnt aufgerufen, bei keinem Treffer, wird undef zurückgegeben.

sub TheOpenTransporter_ProzessInfo($$$)
{
	my ($function, $hash, $fntHash) = @_;
	$function = defined($function) ? uc($function) : "HELP";
			########## Haupt-Funktionen ##################################################
	my %fntTable = (
			"GETDEVICE" => "TheOpenTransporter_FntGetDevice",
			"GETALLDEVICES" => "TheOpenTransporter_FntGetAllDevices",
			"SETDEVICE" => "TheOpenTransporter_FntSetDevice",
			"NEWDEVICE" => "TheOpenTransporter_FntNewDevice",
			"DELDEVICE" => "TheOpenTransporter_FntDelDevice",
			"GETNONDEFINEDDEVICES" => "TheOpenTransporter_FntGetNonDefinedDevices",
			########## Structure-Funktionen ##############################################
			"GETSTRUCTURE" => "TheOpenTransporter_FntGetStructure",
			"GETALLSTRUCTURES" => "TheOpenTransporter_FntGetAllStructures",
			"NEWSTRUCTURE" => "TheOpenTransporter_FntNewStructure",
			"DELETESTRUCTURE" => "TheOpenTransporter_FntDeleteStructure",
			"ADDDEVICEINSTRUCTURE" => "TheOpenTransporter_FntAddDeviceInStructure",
			"DELETEDEVICEINSTRUCTURE" => "TheOpenTransporter_FntDeleteDeviceInStructure",
			"RENEWDEVICEINSTRUCTURE" => "TheOpenTransporter_FntRenewDeviceInStructure",
			########## Log-Funktionen ####################################################
			"GETAVAILABLELOGFILES" => "TheOpenTransporter_FntGetAvailableLogFiles",
			"GETLOGFROMDEVICE" => "TheOpenTransporter_FntGetLogFromDevice",
			"GETSVGFROMDEVICE" => "TheOpenTransporter_FntGetSVGFromDevice",
			########## Script-Funktionen #################################################
			"GETALLSCRIPTS" => "TheOpenTransporter_FntGetAllScripts",
			"GETSCRIPT" => "TheOpenTransporter_FntGetScript",
			"ADDSCRIPT" => "TheOpenTransporter_FntAddScript",
			"DELETESCRIPT" => "TheOpenTransporter_FntDeleteScript",
			"UPDATESCRIPT" => "TheOpenTransporter_FntUpdateScript",
			########## Command-Funktionen ################################################
			"SAVE" => "TheOpenTransporter_FntSave",
			"GETFHEMFILE" => "TheOpenTransporter_FntGetFhemFile",
			"SETFHEMCFG" => "TheOpenTransporter_FntSetFhemCfg",
			"UPDATEFHEM" => "TheOpenTransporter_FntUpdateFhem",
			########## Help-Funktionen ###################################################
			"HELP"	=> "TheOpenTransporter_FntGetHelp",
			"GETVERSION"	=> "TheOpenTransporter_FntGetVersion",
			"EXECUTE"	=>	"TheOpenTransporter_Execute"
			);
	return (exists $fntTable{$function}) ? TheOpenTransporter_CallFnt($fntTable{$function}, $hash, $fntHash) : undef;
}

#########################################################################################################################################################
# Die Methode TheOpenTransporter_CallFnt ruft die ihr übergebende Funktion auf. Dazu wird per shift Operation das erste Element des
# übergebenen Array aus dem Array gezogen (Array enthält danach diese Element nicht mehr).
# Das nun herausgezogene Element beinhaltet den Funktionsnamen.
# no strict "refs" muss ausgeschaltet werden, damit eine Variable als Funktionsnamen dienen darf.
# Danach muss strict wieder angeschaltet werden.

sub TheOpenTransporter_CallFnt($@)
{
	my ($function, @param) = @_;
	if(!$function)
	{
		return undef;
	}
	no strict "refs";
	my $ret = &{$function}(@param);
	use strict "refs";
	return $ret;
}

#########################################################################################################################################################
# Diese Methode wird bei einer Rückantwort einer HTTP Anfrage genutzt, um Fehlermeldungen in das globale Logfile zu
# schreiben und um eine in JSON geparste Antwort zu geben.
# Dabei ist $msg die Meldung, $isError is bei undef eine Statusmeldung, ansonsten eine Fehlermeldung und $logLevel
# gibt dabei die Priorität der Meldung an.

sub TheOpenTransporter_ReturnMessage($$$$)
{
	my ($hash, $msg, $isError, $logLevel) = @_;
	TheOpenTransporter_Log($hash, $logLevel, $msg);
	return to_json { TYPE => (defined($isError)) ? "Error" : "Response", MESSAGE => $msg };
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetVersion($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# <Hauptversionsnummer>.<Nebenversionsnummer>.<Revisionsnummer>
	# z.B. 2.3.5 == 2. Version des Programms, in der 3. Nebenversion und in der 5. Fehlerkorrektur
	$fntHash->{"Content"} = to_json { Version => $hash->{VERSION} };
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_ArrayToJSONArray(@)
{
	my (@array) = @_;
	my $bigJson = '{"Content":[';
	if (@array == 0)
	{
		return $bigJson."]}";
	}
	for (my $i = 0; $i < @array-1; $i++)
	{
		$bigJson .= $array[$i].", ";
	}
	$bigJson .= $array[@array-1]."]}";
	return $bigJson;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetHelp($$)
{
	my ($hash, $fntHash) = @_;
	# öffne Datei TOTHelp.html
	if(!open(FH, $attr{global}{modpath}."/FHEM/TOTHelp.html"))
	{
		Log 2, "Datei kann nicht gefunden werden\n";
		return undef;
	}
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "text/html";
	# füge gesamten Inhalt aus HTML Datei dem Content hinzu
	$fntHash->{"Content"} = join("", <FH>);
	close(FH);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetDevice($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# enthält den Namen des angeforderten Devices oder undef, wenn der Parameter nicht existiert
	my $device = $fntHash->{"param"}->{"NAME"};
	if (!defined($device))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# check, ob das Device in Fhem existiert
	if (!exists $defs{$device})
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Device ".$device." existiert nicht!", 1, 4);
		return undef;
	}
	my %sendHash = %{$defs{$device}};
	$sendHash{guid} = $defs{$device}{NR};
	$sendHash{ATTRIBUTES} = getAllAttr($device);
	$sendHash{SETS}   = getAllSets($device);
	$sendHash{EATTR}   = $attr{$device};

	# Log 2, "$hash->{NAME} on port $hash->{PORT} opened! (Version: $hash->{VERSION})";
	print map { "$_: $sendHash{$_}\n" } keys %sendHash;
	my $debug = map { "$_: $sendHash{$_}\n" } keys %sendHash;
	TheOpenTransporter_Log($hash, 1, "\nsendHash: \n" . $debug . "\n\n");


	$fntHash->{"Content"} = JSON->new->allow_nonref->allow_blessed->utf8->encode(\%sendHash);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetAllDevices($$)
{
	my ($hash, $fntHash) = @_;
	my $showAll = (defined($fntHash->{"param"}->{"OPT"}) && ($fntHash->{"param"}->{"OPT"} eq "showAll")) ? 1 : 0;
	# enthält nach der Schleife alle Devices in Form von JSON Container
	my @devArray;
	# durchläuft in der Hashtable %defs alle Devices von fhem
	foreach my $dev (keys %defs)
	{
		if (!$showAll)
		{
			# Filterung aller Devices die nicht gesendet werden sollen
			next if ($defs{$dev}{TYPE} eq "FileLog");
			next if ($defs{$dev}{TYPE} eq "notify");
			next if ($defs{$dev}{TYPE} eq "at");
			next if ($defs{$dev}{TYPE} eq "structure");
			next if ($defs{$dev}{TYPE} eq "FHEMWEB");
			next if ($defs{$dev}{TYPE} eq "TheOpenTransporter");
			next if ($defs{$dev}{TYPE} eq "weblink");
			next if ($defs{$dev}{TYPE} eq "_internal_");
			next if ($defs{$dev}{TYPE} eq "sequence");
		}
		# bestimmen des Devices
		$fntHash->{"param"}->{"NAME"} = $dev;
		# Aufruf der GetDevice Methode um Device zu erhalten
		TheOpenTransporter_FntGetDevice($hash, $fntHash);
		# JSON Container in das Array schreiben
		push @devArray, $fntHash->{"Content"};	
	}
	# Erzeugt aus allen JSON Containern als ein JSON
	$fntHash->{"Content"} = TheOpenTransporter_ArrayToJSONArray(@devArray);
	return undef;
}

#########################################################################################################################################################
# SetDevice ändert Atribute oder Sets oder DEF des angegeben Devices
#Anfrage: http:// <host> : <port> / SetDevice ? NAME=<device> & <set>=<set_value>
#Anfrage: http:// <host> : <port> / SetDevice ? NAME=<device> & <attr>=<attr_value>
#Anfrage: http:// <host> : <port> / SetDevice ? NAME=<device> & DEF=<def_value>

sub TheOpenTransporter_FntSetDevice($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	my $deviceName = $fntHash->{"param"}->{"NAME"};
	# prüft ob der Parameter NAME vorhanden ist
	if (!defined($deviceName))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# prüft, ob das Device in fhem existiert
	if (!exists($defs{$deviceName}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, 
			"Das angegebene Device ($deviceName) existiert nicht!", 1, 4);
		return undef;
	}
	# prüft ab, ob Attribute zum ändern vorhanden sind
	if ((keys %{$fntHash->{"param"}}) < 2)
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Mindestens ein Attribut angeben!", 1, 4);
		return undef;
	}
	# enthält zum Ende der Methode alle Meldungen
	my @content;
	# holen der Verfügbaren zu ändernden Attribute
	my $availableAttr = getAllAttr($deviceName);
	# holen der Verfügbaren zu ändernden Sets
	my $availableSets = getAllSets($deviceName);
	# durchläuft alle angegebenen Parameter in der Hashtable $fntHash->{"param"}
	foreach my $type (keys %{$fntHash->{"param"}})
	{
		my $typeValue = $fntHash->{"param"}->{$type} || "";
		# da in der Parameter-Hash-Table auch der Name des devices steht, darf dieser nicht beachtet werden
		if ($type =~ "NAME")
		{
			next;
		}	
		# Type is a "set"
		if ($availableSets =~ $type || $type =~ "STATE")
		{
			# Nur wegen der Abwärtskompatibilitöt drinne :(
			my $msg = ( $type =~ "STATE" ) ? "$deviceName $typeValue" : "$deviceName $type $typeValue";
			$msg = CommandSet(undef, $msg);
			# Abfrage der Länge, da bei Erfolg ein leerer String von fhem geliefert wird
			if (defined($msg) && length($msg) > 0)
			{
				push @content, TheOpenTransporter_ReturnMessage($hash, $msg, 1, 5);
			}
			# else: CommandSet was successful
			next;
		}
		# Type is a "attribute"
		if ($availableAttr =~ $type)
		{
			# bei undef wird das Attribut gelöscht
			if ($typeValue =~ "undef")
			{
				my $msg = CommandDeleteAttr(undef, "$deviceName $type");
				# Aufruf der Delete Methode, Rückgabewert von fhem wird beachtet
				# Abfrage der Länge, da bei Erfolg ein leerer String von fhem geliefert wird
				if (defined($msg) && length($msg) > 0)
				{
					push @content, TheOpenTransporter_ReturnMessage($hash, $msg, 1, 5);
				}
				# else: CommandDeleteAttr was successful
			}
			# else: tritt ein, wenn ein Attribut geändert werden muss
			else
			{
				# NICHT auf der FHEM Webseite F5 drücken !!!!!, da ansonsten das Attribut neu gesetzt wird
				my $msg = CommandAttr(undef, "$deviceName $type $typeValue");
				# Abfrage der Länge, da bei Erfolg ein leerer String von fhem geliefert wird
				if (defined($msg) && length($msg) > 0)
				{
					push @content, TheOpenTransporter_ReturnMessage($hash, $msg, 1, 5);
				}
			}	
			next;
		}
		# tritt ein, wenn DEF geändert werden soll
		if ($type eq "DEF")
		{
			my $msg = CommandModify(undef, "$deviceName $typeValue");
			# Aufruf der Modify Methode, Rückgabewert von fhem wird beachtet
			# Abfrage der Länge, da bei Erfolg ein leerer String von fhem geliefert wird
			if (defined($msg) && length($msg) > 0)
			{
				push @content, TheOpenTransporter_ReturnMessage($hash, $msg, 1, 5);
			}
			next;
		}
		# else: tritt ein, wenn nichts zutrifft
		push @content, TheOpenTransporter_ReturnMessage($hash, "[$type = $typeValue] existiert nicht!", 1, 5);
	}
	# Erzeugt aus allen JSON Containern ein JSON Container
	$fntHash->{"Content"} = TheOpenTransporter_ArrayToJSONArray(@content);
	# Erfolgsmeldung bzw Fehlermeldung wird verpackt und Content zugewiesen
	$fntHash->{"Content"} = (length($fntHash->{"Content"}) > 14) ? $fntHash->{"Content"} : TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################
# Die Funktion TOT_FntNewDevice($) wird von TOT_ProzessInfo() aufgerufen und legt dabei mit der Funktion CommandDefine aus fhem.pl
# ein neues Device an.

sub TheOpenTransporter_FntNewDevice($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# prüft auf Parameter Devicename
	if (!defined($fntHash->{"param"}->{"NAME"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "NAME muss angegeben werden!", 1, 4);
		return undef;
	}
	# prüft auf Parameter Devicetype
	if (!defined($fntHash->{"param"}->{"TYPE"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "TYPE muss angegeben werden!", 1, 4);
		return undef;
	}
	# prüft auf Parameter Device Definitionen
	if (!exists($fntHash->{"param"}->{"DEF"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "DEF muss angegeben werden!", 1, 4);
		return undef;
	}
	# aufrufen der fhem Methode CommandDefine
	$fntHash->{"Content"} = (defined($fntHash->{"param"}->{"DEF"})) ? CommandDefine(undef, 
		$fntHash->{"param"}->{"NAME"}." ".$fntHash->{"param"}->{"TYPE"}." ".$fntHash->{"param"}->{"DEF"}) : 
		CommandDefine(undef, $fntHash->{"param"}->{"NAME"}." ".$fntHash->{"param"}->{"TYPE"});
	# Bei Fehlern wird der fhem Fehler zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (defined($fntHash->{"Content"}) && (length($fntHash->{"Content"}) != 0)) ? 
		TheOpenTransporter_ReturnMessage($hash, $fntHash->{"Content"}, 1, 3) : TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################
# FntDeleteDevice löscht das Device mit dem Übergabeparameter NAME in der Hash-Table $fntHash->{"param"}.

sub TheOpenTransporter_FntDelDevice($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# Abfrage, ob Parameter NAME übergeben wurde
	if (!defined($fntHash->{"param"}->{"NAME"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# löschen des Devices unter Berücksichtigung der Fehlermeldung von fhem
	$fntHash->{"Content"} = CommandDelete(undef, $fntHash->{"param"}->{"NAME"});
	# Bei Fehlern wird der fhem Fehler zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (length($fntHash->{"Content"}) > 0) ? TheOpenTransporter_ReturnMessage($hash, $fntHash->{"Content"}, 1, 4) :  
		TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetNonDefinedDevices($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# Name des Hauptmodul von TOT holen
	my $mainTOT = (split(/:/, $hash->{NAME}))[0];
	# check, ob undefList in Hash von mainTOT exitiert
	if (!exists($defs{$mainTOT}{undefList}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "undefList existiert nicht!", 1, 5);
		return undef;
	}
	my @buffer;
	my @jsonUndefList;
	for (my $i = 0; $i < @{$defs{$mainTOT}{undefList}}; $i++)
	{
		my $undefType = (split(/ /, $defs{$mainTOT}{undefList}[$i]))[2];
		my $undefDef = (split(/ /, $defs{$mainTOT}{undefList}[$i]))[3];
		my $done;
		foreach my $dev (keys %defs)
		{
			# trifft diese Bedingung zu, exitiert bereits ein Devices des TYPS
			if (($undefType eq $defs{$dev}{TYPE}) && ($defs{$dev}{DEF} =~ $undefDef))
			{
				$done = 1;
				last;
			}
		}	
		if (defined($done))
		{
			next;
		}
		push @buffer, $defs{$mainTOT}{undefList}[$i];
		my @undefParam = split(" ", $defs{$mainTOT}{undefList}[$i]);
		my %wrapper = ( guid => $i );
		for (my $j = 0; $j < @undefParam; $j++)
		{
			$wrapper{$j} = $undefParam[$j];
		}
		push @jsonUndefList, to_json {%wrapper};
	}
	$defs{$mainTOT}{undefList} = \@buffer;
	# Erzeugt aus allen JSON Containern ein JSON Container
	$fntHash->{"Content"} = TheOpenTransporter_ArrayToJSONArray(@jsonUndefList);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetStructure($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# enthält den Namen der angeforderten Struktur oder undef, wenn der Parameter nicht existiert
	my $struct = $fntHash->{"param"}->{"NAME"};
	if (!defined($struct))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	if (!exists($defs{$struct}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Struktur ".$struct." existiert nicht!", 1, 4);
		return undef;
	}
	if($defs{$struct}{TYPE} ne "structure")
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash,  $struct." ist keine Struktur!", 1, 4);
		return undef;
	}
	my %sendHash = %{$defs{$struct}};
	$sendHash{guid} = $defs{$struct}{NR};
	$sendHash{ATTRIBUTES} = getAllAttr($struct);
	$sendHash{SETS}   = getAllSets($struct);
	# JSON Parser wird aufgerufen und Eigenschaften der Structure werden übergeben und in Content geschrieben
	$fntHash->{"Content"} = JSON->new->allow_nonref->allow_blessed->utf8->encode(\%sendHash);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetAllStructures($$)
{
	my ($hash, $fntHash) = @_;
	# enthält nach der Schleife alle Strukturen in Form von JSON Container
	my @structArray;
	# durchläuft in der Hashtable %defs alle Strukturen von fhem
	foreach my $struct (keys %defs)
	{
		# Filterung nach den Strukturen
		next if ($defs{$struct}{TYPE} ne "structure");
		# festlegen der Struktur
		$fntHash->{"param"}->{"NAME"} = $struct;
		# Aufruf der FntGetStructure Methode um Strukturen zu erhalten
		TheOpenTransporter_FntGetStructure($hash, $fntHash);
		# JSON Container in das Array schreiben
		push @structArray, $fntHash->{"Content"};
	}
	# Erzeugt aus allen JSON Containern ein JSON Container
	$fntHash->{"Content"} = TheOpenTransporter_ArrayToJSONArray(@structArray);
	return undef;
}

#########################################################################################################################################################
# <structure_name>, <structure_type>, <dev1>, [Optional: <dev2>, ..., <devn>]

sub TheOpenTransporter_FntNewStructure($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# prüft auf Parameter Strukturename
	if (!defined($fntHash->{"param"}->{"NAME"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!!", 1, 4);
		return undef;
	}
	# prüft auf Parameter Strukturtype
	if (!defined($fntHash->{"param"}->{"ATTR"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter ATTR fehlt!!", 1, 4);
		return undef;
	}
	# prüft auf Parameter Struktur Definitionen
	if (!defined($fntHash->{"param"}->{"DEVICES"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter DEVICES fehlt!!", 1, 4);
		return undef;
	}
	# aufrufen der fhem Methode CommandDefine
	$fntHash->{"Content"} = CommandDefine(undef, $fntHash->{"param"}->{"NAME"}." structure ".$fntHash->{"param"}->{"ATTR"}.
		" ".$fntHash->{"param"}->{"DEVICES"});
	# Bei Fehlern wird der fhem Fehler zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (defined($fntHash->{"Content"}) && (length($fntHash->{"Content"}) != 0)) ? 
		TheOpenTransporter_ReturnMessage($hash, $fntHash->{"Content"}, 1, 3) : TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################
sub TheOpenTransporter_FntDeleteStructure($$)
{
	my ($hash, $fntHash) = @_;
	# Aufruf von FntDeldevice, da dies im Prinzip das Gleiche ist
	return TheOpenTransporter_FntDelDevice($hash, $fntHash);
}

#########################################################################################################################################################
# <structure_name> <dev_name1> [<dev_name2> ... <dev_nameN>]

sub TheOpenTransporter_FntAddDeviceInStructure($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# check, ob Parameter NAME vorhanden und defined
	if (!defined($fntHash->{"param"}->{"NAME"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# check, ob Parameter DEVICES vorhanden und defined
	if (!defined($fntHash->{"param"}->{"DEVICES"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter DEVICES fehlt!", 1, 4);
		return undef;
	}
	# prüft, ob die Struktur in fhem existiert
	if (!exists($defs{$fntHash->{"param"}->{"NAME"}}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, 
			"Die angegebene Struktur (".$fntHash->{"param"}->{"NAME"}.") existiert nicht!", 1, 4);
		return undef;
	}
	# splitten nach allen Leerzeichen
	my @device = split(/ /, $fntHash->{"param"}->{"DEVICES"});
	# Flag-Variable, wird gesetzt, wenn Methode erfolgreich war
	my $done;
	# durchläuft all hinzuzufügenden Devices
	for (my $i = 0; $i < @device; $i++)
	{
		# auf existierendes Device prüfen
		if (!($defs{$fntHash->{"param"}->{"NAME"}}{STATE} =~ $device[$i]))
		{
			$defs{$fntHash->{"param"}->{"NAME"}}{STATE} .= " ".$device[$i];
			$defs{$fntHash->{"param"}->{"NAME"}}{DEF} .= " ".$device[$i];
			$done = 1;
		}
	}
	# Bei Fehlern wird false als Fehler zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (defined($done)) ? TheOpenTransporter_ReturnMessage($hash, "true", undef, 5) : 
		TheOpenTransporter_ReturnMessage($hash, "false", 1, 4);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntDeleteDeviceInStructure($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# check, ob Parameter NAME vorhanden und defined
	if (!defined($fntHash->{"param"}->{"NAME"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!!", 1, 4);
		return undef;
	}
	# check, ob Parameter DEVICES vorhanden und defined
	if (!defined($fntHash->{"param"}->{"DEVICES"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter DEVICES fehlt!!", 1, 4);
		return undef;
	}
	# prüft, ob die Struktur in fhem existiert
	if (!exists($defs{$fntHash->{"param"}->{"NAME"}}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, 
			"Die angegebene Struktur (".$fntHash->{"param"}->{"NAME"}.") existiert nicht!", 1, 4);
		return undef;
	}
	# splitten nach allen Leerzeichen
	my @device = split(/ /, $fntHash->{"param"}->{"DEVICES"});
	# prüft, ob in der Struktur ein Device gelöscht werden kann (1 muss immer vorhanden sein)
	my @anzDev = split(/ /, $defs{$fntHash->{"param"}->{"NAME"}}{STATE});
	if (@device >= @anzDev)
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Zu viele zu loeschende Devices!", 1, 4);
		return undef;
	}
	# Flag-Variable, wird gesetzt, wenn Methode erfolgreich war
	my $done;
	# durchläuft alle zu setzenden Devices
	for (my $i = 0; $i < @device; $i++)
	{
		# tritt ein, wenn STATE der Struktur das zu löschende Device enthält
		if ($defs{$fntHash->{"param"}->{"NAME"}}{STATE} =~ $device[$i])
		{
			# zu löschendes Device entfernen
			$defs{$fntHash->{"param"}->{"NAME"}}{STATE} =~ s/$device[$i]//;
			# Doppeltes Leerzeichen durch ein Leerzeichen ersetzen
			$defs{$fntHash->{"param"}->{"NAME"}}{STATE} =~ s/  / /;
			# Leerzeichen am Ende entfernen
			$defs{$fntHash->{"param"}->{"NAME"}}{STATE} =~ s/\s+$//g;
			# Leerzeichen am Anfang entfernen
			$defs{$fntHash->{"param"}->{"NAME"}}{STATE} =~ s/^\s+//g ;
			# Flag setzen
			$done = 1;
		}
	}
	# DEF aktualisieren
	$defs{$fntHash->{"param"}->{"NAME"}}{DEF} = $defs{$fntHash->{"param"}->{"NAME"}}{ATTR}." ".$defs{$fntHash->{"param"}->{"NAME"}}{STATE};
	# Bei Fehlern wird false als Fehler zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (defined($done)) ? TheOpenTransporter_ReturnMessage($hash, "true", undef, 5) : 
		TheOpenTransporter_ReturnMessage($hash, "false", 1, 4);
	return undef;
}

#########################################################################################################################################################
# SetDeviceInStructure setzt alle übergebenden Devices in die Struktur
# Damit werden alle alten Devices in der Struktur überschrieben.

sub TheOpenTransporter_FntRenewDeviceInStructure($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# prüft auf Parameter Strukturname
	if (!defined($fntHash->{"param"}->{"NAME"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# prüft auf Parameter Struktur Definitionen
	if (!defined($fntHash->{"param"}->{"DEVICES"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter DEVICES fehlt!", 1, 4);
		return undef;
	}
	# prüft, ob die Struktur in fhem existiert
	if (!exists($defs{$fntHash->{"param"}->{"NAME"}}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, 
			"Die angegebene Struktur (".$fntHash->{"param"}->{"NAME"}.") existiert nicht!", 1, 4);
		return undef;
	}
	# STATE mit allen nachfolgenden Werten überschreiben
	$defs{$fntHash->{"param"}->{"NAME"}}{STATE} = $fntHash->{"param"}->{"DEVICES"};
	# DEF aktualisieren
	$defs{$fntHash->{"param"}->{"NAME"}}{DEF} = $defs{$fntHash->{"param"}->{"NAME"}}{ATTR}." ".$defs{$fntHash->{"param"}->{"NAME"}}{STATE};
	# Erfolgsmeldung wird verpackt und Content zugewiesen
	$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetAvailableLogFiles($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# Array welches nach der Schleife alle existierenden Logfiles enthält
	my @logFileList;
	# durchläuft alle Devices
	foreach my $key (keys %defs)
	{
		# Filterung nach logfiles, ansonsten wird weitergesprungen
		if (!exists($defs{$key}{"logfile"}))
		{
			next;
		}
		# schiebt das Logfile JSOn Objekt in das logFileList Array
		push @logFileList, to_json {	guid => $defs{$key}{NR},
						NAME => $key,
						TYPE => $defs{$key}{TYPE}, 
						LOGFILE => $defs{$key}{logfile}
						};
	}
	# Erzeugt aus allen JSON Containern ein JSON Container
	$fntHash->{"Content"} = TheOpenTransporter_ArrayToJSONArray(@logFileList);
	return undef;
}

#########################################################################################################################################################
# Return:	Fehler		: undef
#		Start < End	: -1
#		Start == End	: 0
#		Start > End	: 1
#
# Da diese Methode in einem Zustand aufgerufen wird, bei dem das LogFile ausgelesen wird, darf in der Methode auf keinen Fall
# ein Log Eintrag getätigt werden, da ansonsten ein nicht deterministischer Zustand erzeugt wird (Endlosschleife).
# VERBOTEN: KEIN Log!!!

sub TheOpenTransporter_DateTimeEqual(@)
{
	# um Datum und Zeit zu vergleichen, müssen gleich viele start und end Parameter vorhanden sein
	if (@_ % 2 != 0)
	{
		return undef;
	}
	# Offset für ersten Index des Enddatums
	my $offset = @_ / 2;
	# Vergleich Start- und End- (-jahr,-monat,-tag,-stunde,-minute,-sekunde)
	# Setze mit Index Jahr auf Monat, Monat auf Tag, Tag auf Stunde, Stunde auf Minute, Minute auf Sekunde
	for (my $index = 0; $index < $offset ; $index++)
	{
		if ($_[$index] > $_[$index+$offset])
		{
			return 1;
		}
		# Vergleich Startjahr und EndJahr
		if ($_[$index] < $_[$index+$offset])
		{
			return -1;
		}
	}
	return 0;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetLogFromDevice($$)
{
	my ($hash, $fntHash) = @_;
	# weist den Namen des auszulesenden Logfiles zu
	my $logFile = $fntHash->{"param"}->{"NAME"};
	if (!defined($logFile))
	{
		# festlegen des ContentTypes für die zurückzusendende Nachricht
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 5);
		return undef;
	}
	# prüft, ob das Device in fhem existiert
	if (!exists($defs{$logFile}{"logfile"}))
	{
		# festlegen des ContentTypes für die zurückzusendende Nachricht
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Das Logfile (".$logFile.") existiert nicht!", 1, 5);
		return undef;
	}
	# Abfrage auf passende Anzahl der Datum und Zeit angeben welche angegeben werden können
	if ((keys %{$fntHash->{"param"}}) > 10)
	{
		# festlegen des ContentTypes für die zurückzusendende Nachricht
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, 
			"wrong syntax: <device_name> [Start Datum: [YYYY] [MM] [TT] [HH] [MM]] [End Datum: [YYYY] [MM] [TT] [HH] [MM]]", 1, 4);
		return undef;
	}	
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "plain/text";
	# lese aktuelles Datum und aktuelle Zeit aus
	my ($aktSec, $aktMin, $aktHour, $aktDay, $aktMonth, $aktYear) = localtime();
	# addieren von 1900, da die Jahreszählung von 1900 mit 0 beginnt
	$aktYear += 1900;
	# addieren von 1, da Monatszählung ebenfalls bei 0 beginnt
	$aktMonth += 1;
	# anhängen einer 0, wenn Zahlen kleiner 10 sind
	if ($aktMonth < 10)
	{
		$aktMonth = "0".$aktMonth;
	}
	# bei einem Parameter wird lediglich das aktuelle Logfile angefordert ohne Filterung
	if ((keys %{$fntHash->{"param"}}) == 1)
	{
		# lese Speicherort des LogFile aus 
		my $fileName = $defs{$logFile}{"logfile"};
		# bei allgemeinen Dateiangaben muss noch das allgemein angegebene Jahr (%Y) durch das aktuelle Jahr ersetzt werden
		$fileName =~ s/%Y/$aktYear/;
		# bei allgemeinen Dateiangeben muss noch der allgemein angegebene Monat (%m) durch den aktuellen Monat ersetzt werden
		$fileName =~ s/%m/$aktMonth/;
		if(!open(FH, $fileName))
		{
			$fntHash->{"ContentType"} = "application/json";
			$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Die Datei (".$fileName.") kann nicht geoeffnet werden!", 1, 4);
			return undef;
		}
		$fntHash->{"Content"} = join("", <FH>);
		close(FH);
		return undef;
	}
	###########################################################
	# tritt ein, wenn ein start, end Datum angegeben wurde
	############### Startdatumsbehandlung start ###############
	# prüfen, ob Startjahr existiert, JA => Startjahr festlegen, NEIN => Startjahr auf ein ungültiges Jahr (1900) setzen
	my $startYear = (defined($fntHash->{"param"}->{"SYEAR"})) ? $fntHash->{"param"}->{"SYEAR"} : 2000;
	# prüfen auf gültige Eingabe
	if ($startYear < 1970 || $startYear > $aktYear)
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Das angegebene start Jahr ($startYear) ist ungueltig!", 1, 5);
		return undef;
	}
	# prüfen, ob Startmonat existiert, JA => Startmonat festlegen, NEIN => Startmonat auf 1 setzen
	my $startMonth = (defined($fntHash->{"param"}->{"SMONTH"})) ? $fntHash->{"param"}->{"SMONTH"} : 1;
	# prüfen auf gültige Eingabe
	if (defined($startMonth) && ($startMonth < 1 || $startMonth > 12))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Der angegebene start Monat ($startMonth) ist ungueltig!", 1, 5);
		return undef;
	}
	# prüfen, ob Starttag existiert, JA => Starttag festlegen, NEIN => Starttag auf 1 setzen
	my $startDay = (defined($fntHash->{"param"}->{"SDAY"})) ? $fntHash->{"param"}->{"SDAY"} : 1;
	# festlegen der maximalen Tage pro Monat
	my @maxDaysPerMonth = (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	# Schaltjahr filtern, da der Februar auf 29 Tage gesetzt werden muss
	if ($startYear % 4 == 0 && $startYear % 100 != 0 && $startYear % 400 == 0)
	{
		$maxDaysPerMonth[1] = 29;
	}
	# prüfen auf gültige Eingabe
	if (defined($startDay) && ($startDay < 1 || $startDay > $maxDaysPerMonth[$startMonth-1]))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Der angegebene start Tag ($startDay) ist ungueltig!", 1, 5);
		return undef;
	}
	# prüfen, ob Startstunde existiert, JA => Startstunde festlegen, NEIN => Startstunde auf 0 setzen
	my $startHour = (defined($fntHash->{"param"}->{"SHOUR"})) ? $fntHash->{"param"}->{"SHOUR"} : 0;
	# prüfen auf gültige Eingabe
	if (defined($startHour) && ($startHour < 0 || $startHour > 23))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Die angegebene start Stunde ($startHour) ist ungueltig!", 1, 5);
		return undef;
	}
	# prüfen, ob Startminute existiert, JA => Startminute festlegen, NEIN => Startminute auf 0 setzen
	my $startMin = (defined($fntHash->{"param"}->{"SMIN"})) ? $fntHash->{"param"}->{"SMIN"} : 0;
	# prüfen auf gültige Eingabe
	if (defined($startMin) && ($startMin < 0 || $startMin > 60))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Die angegebene start Minute ($startMin) ist ungueltig!", 1, 5);
		return undef;
	}
	############### Startdatumsbehandlung ende ###############
	############### Enddatumsbehandlung start ################
	# prüfen, ob Endjahr existiert, JA => Endjahr festlegen, NEIN => Endjahr auf das aktuelle Jahr setzen
	my $endYear = (defined($fntHash->{"param"}->{"EYEAR"})) ? $fntHash->{"param"}->{"EYEAR"} : $aktYear;
	# prüfen auf gültige Eingabe
	if ($endYear < 1970 || $endYear > $aktYear)
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Das angegebene end Jahr ($endYear) ist ungueltig!", 1, 5);
		return undef;
	}
	# prüfen, ob Endmonat existiert, JA => Endmonat festlegen, NEIN => letzter Monat des Jahres (12)
	my $endMonth = (defined($fntHash->{"param"}->{"EMONTH"})) ? $fntHash->{"param"}->{"EMONTH"} : 12;
	# prüfen auf gültige Eingabe
	if (defined($endMonth) && ($endMonth < 1 || $endMonth > 12))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Der angegebene end Monat ($endMonth) ist ungueltig!", 1, 5);
		return undef;
	}
	# Schaltjahr filtern, da der Februar auf 29 Tage gesetzt werden muss, bzw auf 28 Tage
	$maxDaysPerMonth[1] = ($endYear % 4 == 0 && $endYear % 100 != 0 && $endYear % 400 == 0) ? 29 : 28;
	# prüfen, ob Endtag existiert, JA => Endtag festlegen, NEIN => Endtag auf letzten Tag des Monats setzen
	my $endDay = (defined($fntHash->{"param"}->{"EDAY"})) ? $fntHash->{"param"}->{"EDAY"} : $maxDaysPerMonth[$endMonth-1];
	# prüfen auf gültige Eingabe
	if (defined($endDay) && ($endDay < 1 || $endDay > $maxDaysPerMonth[$endMonth-1]))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Der angegebene end Tag ($endDay) ist ungueltig!", 1, 5);
		return undef;
	}
	# prüfen, ob Endstunde existiert, JA => Endstunde festlegen, NEIN => Endstunde auf größte Stunde setzen
	my $endHour = (defined($fntHash->{"param"}->{"EHOUR"})) ? $fntHash->{"param"}->{"EHOUR"} : 23;
	# prüfen auf gültige Eingabe
	if (defined($endHour) && ($endHour < 0 || $endHour > 23))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Die angegebene end Stunde ($endHour) ist ungueltig!", 1, 5);
		return undef;
	}
	# prüfen, ob Endminute existiert, JA => Endminute festlegen, NEIN => Endminute auf größte Minute setzen
	my $endMin = (defined($fntHash->{"param"}->{"EMIN"})) ? $fntHash->{"param"}->{"EMIN"} : 59;
	# prüfen auf gültige Eingabe
	if (defined($endMin) && ($endMin < 0 || $endMin > 60))
	{
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Die angegebene end Minute ($endMin) ist ungueltig!", 1, 5);
		return undef;
	}
	############### Enddatumsbehandlung ende ################
	# Info-Log Ausgabe
	TheOpenTransporter_Log($hash, 5, "Eingangsdatum: start: ".$startYear.".".$startMonth.".".$startDay." ".$startHour.":".$startMin.":00");
	TheOpenTransporter_Log($hash, 5, "Eingangsdatum: ende : ".$endYear.".".$endMonth.".".$endDay." ".$endHour.":".$endMin.":00");
	# Inhalt auf leeren String setzten
	$fntHash->{"Content"} = "";
	# zählt Startjahr bis Endjahr hoch
	for (my $cxYear = $startYear; $cxYear <= $endYear; $cxYear++)
	{
		# zählt Startmonat bis Endmonat hoch
		for (my $cxMonth = ($cxYear == $startYear) ? $startMonth : 1; $cxMonth <= (($cxYear == $endYear) ? $endMonth : 12); $cxMonth++)
		{
			# auslesen des Speicherorts vom Logfiles
			my $fileName = $defs{$logFile}{"logfile"};
			# Ersetzen des allgemein angegeben Jahr (%Y) mit aktuellem Jahr
			$fileName =~ s/%Y/$cxYear/;
			# bei einstelligem Monat muss noch ne 0 an die erste Stelle gepackt werden
			if (length($cxMonth) < 2)
			{
				$cxMonth = "0".$cxMonth;
			}
			# Ersetzen des allgemein angegebenen Monats (%m) mit aktuellem Monat
			$fileName =~ s/%m/$cxMonth/;
			# check auf File exists
			if (!(-e $fileName))
			{
				next;
			}
			# öffnen des Files
			if(!open(FH, $fileName))
			{
				$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash,
					"Die Datei (".$fileName.") kann nicht geoeffnet werden!", 1, 2);
				return undef;
			}
			# Schleife über die ganze Datei
			while (<FH>)
			{
				# auf keinen fall ab hier etwas ins Log schreiben => Ping Pong => unendlich wachsende Datei
				# splitten von Datum und Zeit, mit substr wird lediglich Datum und Zeit als ein String geliefert
				my @subDate = split(" ", substr($_, 0, 19));
				# check ob Zeile überhaupt Datum und Zeit enthällt, NEIN => nächste Zeile
				if (@subDate != 2)
				{
					# wenn Content > 0 => startDatum bereits gefunden
					if (length($fntHash->{"Content"}) > 0)
					{
						# Zeile ohne Datum wird dem Content angehangen
						$fntHash->{"Content"} .= $_;
					}
					next;
				}
				# splitten des Datums in seine einzelnen Komponenten (Jahr, Monat, Tag)
				my @date = split(/\./, $subDate[0]);
				# splitten der Zeit in seine Einzelnen Komponenten (Stunde, Minute, Sekunde)
				my @dateTime = split(":", $subDate[1]);
				# check ob Datum und Zeit jeweils 3 Komponenten besitzen, NEIN => nächste Zeile
				if (@date != 3 || @dateTime != 3)
				{
					# wenn Content > 0 => startDatum bereits gefunden
					if (length($fntHash->{"Content"}) > 0)
					{
						# Zeile ohne Datum wird dem Content angehangen
						$fntHash->{"Content"} .= $_;
					}
					next;
				}
				# Entfernt vorangestelte Nullen von den 3 Datumskomponenten
				for (my $i = 0; $i < @date; $i++)
				{
					if (length($date[$i]) == 2 && substr($date[$i],0,1) == 0)
					{
						$date[$i] = substr($date[$i],1,1);
					}
				}
				# Entfernt vorangestellte Nullen von den 3 Datumskomponenten
				for (my $i = 0; $i < @dateTime; $i++)
				{
					if (length($dateTime[$i]) == 2 && substr($dateTime[$i],0,1) == 0)
					{
						$dateTime[$i] = substr($dateTime[$i],1,1);
					}
				}
				# trifft diese Bedingung zu, haben wir ein Datum gefundne das größer als das Enddatum ist
				if (TheOpenTransporter_DateTimeEqual(@date, @dateTime, $endYear, $endMonth, $endDay, $endHour, $endMin, 59) > 0)
				{
					# Suche wird abgebrochen
					last;
				}
				# Erzeugung von Zwei Vergleichen mittels der DateTimeEqual Methode, ausgelesener Datums und Zeit Werte mit Startdatum und
				# ausgelesener Datums und Zeit Wert mit Enddatum, dabei muss gelten StartVergleich >= 0 und EndVergleich <= 0
				if (TheOpenTransporter_DateTimeEqual(@date, @dateTime, 
					$startYear, $startMonth, $startDay, $startHour, $startMin, 0) >= 0)
				{
					# Zeile wird dem Content hinzugefügt
					$fntHash->{"Content"} .= $_;
				}
				# nach Blockende Logs wieder erlaubt
			}
			# schließt das Dateihandle
			close(FH);
		}	
	}
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetSVGFromDevice($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "plain/text";
	# prüft ob der Parameter PARAMS vorhanden ist
	if (!defined($fntHash->{"param"}->{"PARAMS"}))
	{
		# festlegen des ContentTypes für die zurückzusendende Nachricht
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter PARAMS fehlt!", 1, 4);
		return undef;
	}
	# Aufruf der SVG_render Methode aus dem 98_SVG.pm Modul
	my @params = @{$fntHash->{"param"}->{"PARAMS"}};
	if (@params != 8)
	{
		# festlegen des ContentTypes für die zurückzusendende Nachricht
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "PARAMS - Ungültige Parameteranzahl!", 1, 4);
		return undef;
	}
	my $name = $params[0];  # e.g. wl_8
	my $from = $params[1];  # e.g. 2008-01-01
	my $to = $params[2];    # e.g. 2009-01-01
	my $confp = $params[3]; # lines from the .gplot file, w/o FileLog and plot
	my $dp = $params[4];    # pointer to data (one string)
	my $plot = $params[5];  # Plot lines from the .gplot file
	my $parent_name = $params[6];  # e.g. FHEMWEB instance name
	my $parent_dir  = $params[7];  # FW_dir
#TH	$fntHash->{"Content"} = SVG_render($name, $from, $to, $confp, $dp, $plot, $parent_name, $parent_dir);
	return undef;
}

#########################################################################################################################################################
# Diese Methode liefert alle Scripte, indem die Hash %defs durchlaufen und nach TYPE == notify gefiltert wird.

sub TheOpenTransporter_FntGetAllScripts($$)
{
	my ($hash, $fntHash) = @_;
	# Enthält alle nach Ende der Schleife alle notifys
	my @notifyArray;
	# durchläuft alle Devices
	foreach my $notify (keys %defs)
	{
		# Filtert alle Devices die kein notifys sind
		next if ($defs{$notify}{TYPE} ne "notify" && $defs{$notify}{TYPE} ne "at");
		
		# setzt den Namen des notifys
		$fntHash->{"param"}->{"NAME"} = $notify; 
		# holt sich das notify mithilfe der Methode FntGetScript
		TheOpenTransporter_FntGetScript($hash, $fntHash);
		# notify wird in Array gespeichert
		push @notifyArray, $fntHash->{"Content"};
	}
	# Erzeugt aus allen JSON Containern ein JSON Container
	$fntHash->{"Content"} = TheOpenTransporter_ArrayToJSONArray(@notifyArray);
	return undef;
}

#########################################################################################################################################################
# Diese Methode liefert ein Script, indem die Hash %defs durchlaufen und nach TYPE == notify und NAME gefiltert wird.

sub TheOpenTransporter_FntGetScript($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# enthält den Namen des angeforderten Scripts oder undef, wenn der Parameter nicht existiert
	my $notify = $fntHash->{"param"}->{"NAME"};
	# prüft, ob der Parameter NAME vorhanden ist
	if (!defined($notify))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# Entfernt aus notify alle Leerzeichen uns gibt die Anzahl der Entfernten Leerzeichen zurück
	if (($notify =~ tr/ //d) > 0)
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Der Parameter NAME darf keine Leerzeichen enthalten!", 1, 4);
		return undef;
	}
	# check, ob das Script in Fhem existiert
	if (!exists $defs{$notify})
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Das Script ".$notify." existiert nicht!", 1, 4);
		return undef;
	}
	# check auf Script
	if($defs{$notify}{TYPE} ne "notify")
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash,  $notify." ist kein Script!", 1, 4);
		return undef;
	}
	my %sendHash = %{$defs{$notify}};
	$sendHash{COM} = substr($defs{$notify}{DEF}, index($defs{$notify}{DEF}, " ") + 1);
	$sendHash{guid} = $defs{$notify}{NR};
	$sendHash{ATTRIBUTES} = getAllAttr($notify);
	$sendHash{SETS}   = getAllSets($notify);
	# JSON Parser wird aufgerufen und Eigenschaften des Scriptes werden übergeben und in Content geschrieben
	$fntHash->{"Content"} = JSON->new->allow_nonref->allow_blessed->utf8->encode(\%sendHash);
	return undef;
}

#########################################################################################################################################################
# Usage: define <name> notify <regexp> <command>

sub TheOpenTransporter_FntAddScript($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# Check auf existenz von NAME in der Parameter-Hash-Table
	if (!defined($fntHash->{"param"}->{"NAME"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# Check auf existenz von REGEXP in der Parameter-Hash-Table
	if (!defined($fntHash->{"param"}->{"REGEXP"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter REGEXP fehlt!", 1, 4);
		return undef;
	}
	# Check auf existenz von COM in der Parameter-Hash-Table
	if (!defined($fntHash->{"param"}->{"COM"}))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter COM fehlt!", 1, 4);
		return undef;
	}
	$fntHash->{"Content"} = CommandDefine(undef, 
		$fntHash->{"param"}->{"NAME"}." notify ".$fntHash->{"param"}->{"REGEXP"}." ".$fntHash->{"param"}->{"COM"});
	# Bei Fehlern wird der fhem Fehler zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (defined($fntHash->{"Content"})) ? TheOpenTransporter_ReturnMessage($hash, $fntHash->{"Content"}, 1, 4) : 
		TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntDeleteScript($$)
{
	my ($hash, $fntHash) = @_;
	# Aufruf von FntDelDevice, da dies prinzipell das Gleiche ist
	return TheOpenTransporter_FntDelDevice($hash, $fntHash);
}

#########################################################################################################################################################
# Da bei dieser der NAME, REGEXP, sowie COM übergeben wird, wird lediglich das alte Script gelöscht und neu angelegt.

sub TheOpenTransporter_FntUpdateScript($$)
{
	my ($hash, $fntHash) = @_;


	# Aufruf von FntDeleteScript
	TheOpenTransporter_FntDeleteScript($hash, $fntHash);
	# Aufruf con FntAddScript
	TheOpenTransporter_FntAddScript($hash, $fntHash);

	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntSave($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# Aufruf von CommandSave (Fhem Methode)
	$fntHash->{"Content"} = CommandSave(undef, undef);
	# Bei Fehlern wird der fhem Fehler zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (defined($fntHash->{"Content"})) ? TheOpenTransporter_ReturnMessage($hash, $fntHash->{"Content"}, 1, 4) : 
		TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntGetFhemFile($$)
{
	my ($hash, $fntHash) = @_;
	# weist den Namen zu, wenn der Wert in der Hashtable vorhanden ist, ansonsten wird "not_defined" zugewiesen
	my $fhemFileName = (exists($fntHash->{"param"}->{"NAME"})) ? $fntHash->{"param"}->{"NAME"} : "not_defined";
	# prüft, ob das fhemFileName existiert
	if ($fhemFileName eq "not_defined")
	{
		# festlegen des ContentTypes für die zurückzusendende Nachricht
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter NAME fehlt!", 1, 4);
		return undef;
	}
	# festlegen des ContentTypes für die zurückzusendende Nachricht (Textfile => plain/text)
	$fntHash->{"ContentType"} = "plain/text";
	# enthält der Parameter NAME = fhem.cfg wird die fhem.cfg geliefert, ansonsten wird der angegebene FileNAME verwendet
	$fhemFileName = ($fhemFileName eq "fhem.cfg") ? $attr{global}{configfile} : $attr{global}{modpath}."/FHEM/".$fhemFileName;
	# versucht die Datei zum schreiben zu öffnen
	if(!open(FH, $fhemFileName))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Can't open ".$fhemFileName, 1, 4);
		return undef;
	}
	$fntHash->{"Content"} = join("", <FH>);
	close(FH);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntSetFhemCfg($$)
{
	my ($hash, $fntHash) = @_;
	# prüft, ob der Parameter DATA existiert
	if (!exists($fntHash->{"param"}->{"DATA"}))
	{
		# festlegen des ContentTypes für die zurückzusendende Nachricht
		$fntHash->{"ContentType"} = "application/json";
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter DATA fehlt!", 1, 4);
		return undef;
	}
	my $fileName = $attr{global}{configfile};
	# Log Information
	TheOpenTransporter_Log($hash, 5, "SetFhemCfg: Die Datei ".$fileName." wurde ueberschrieben.");
	my $fileHandle = IO::File->new();
	if (!($fileHandle->open(">".$fileName)))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Die Datei (".$fileName.") kann nicht geoeffnet werden!", 1, 2);
		return undef;
	}
	$fileHandle->print($fntHash->{"param"}->{"DATA"});
	close($fileHandle);
	$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_FntUpdateFhem($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	# sucht nach Parameter OPT (Optionen), wenn vorhanden setzte $options = OPT, andernfalls setzte $options auf leeren String ("")
	my $options = (defined($fntHash->{"param"}->{"OPT"})) ? $fntHash->{"param"}->{"OPT"} : "";
	# Aufruf der CommandUpdateFhem Methode aus dem 99_updatefhem.pm Modul
	$fntHash->{"Content"} = CommandUpdatefhem(undef, $options);
	# Bei Fehlern wird der Fehler der 99_updatefhem.pm zurückgeliefert, ansonsten wird true als Erfolg geliefert
	$fntHash->{"Content"} = (defined($fntHash->{"Content"}) && (length($fntHash->{"Content"}) != 0)) ? 
		TheOpenTransporter_ReturnMessage($hash, $fntHash->{"Content"}, 1, 4) : TheOpenTransporter_ReturnMessage($hash, "true", undef, 5);
	return undef;
}

#########################################################################################################################################################

sub TheOpenTransporter_PackageFunctionExists($$)
{
	my ( $libName, $function ) = @_;
	no strict "refs";
	my %fntTable = %{$libName."::"};
	use strict "refs";
	return exists $fntTable{$function};
}

#########################################################################################################################################################

sub TheOpenTransporter_Execute($$)
{
	my ($hash, $fntHash) = @_;
	# festlegen des ContentTypes für die zurückzusendende Nachricht
	$fntHash->{"ContentType"} = "application/json";
	my $function = $fntHash->{"param"}->{"FNT"};
	# prüft ob der Parameter FNT vorhanden ist
	if (!defined($function))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "Parameter FNT fehlt!", 1, 4);
		return undef;
	}
	# check if calling function exists
	if (!TheOpenTransporter_PackageFunctionExists("main", $function))
	{
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, "FNT $function existiert nicht!", 1, 4);
		return undef;
	}
	
	my @fntParam;
	for (my $i = 0; exists($fntHash->{"param"}->{"PARAM$i"}); $i++)
	{
		if (exists($fntHash->{"param"}->{"PARAM$i"}))
		{
			my $value = ( $fntHash->{"param"}->{"PARAM$i"} =~ "undef" ) ? 
				undef : $fntHash->{"param"}->{"PARAM$i"};
			push( @fntParam, $value );
		}
	}
	
	my $ret;
	# call function with try catch statement
	eval
	{
		$ret = (@fntParam > 0) ? TheOpenTransporter_CallFnt($function, @fntParam) 
			: TheOpenTransporter_CallFnt($function);
	};
	# error handling
	if ($@)
	{
		$ret = $@;
		$ret =~ s/\n$//g;
		
		# return error
		$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, defined($ret) ? $ret : "undef", 1, 4);
		return undef;
	}
	# return crazy things
	$fntHash->{"Content"} = TheOpenTransporter_ReturnMessage($hash, defined($ret) ? $ret : "undef", undef, 5);
	return undef;
}

# ToDo: backup Funktion hinzufügen (nur von fhem aufrufen)
# ToDo: authentifikation user / password (mit olli abquatschen)
# ToDo: aus der Modulsammlung ne tolle Klasse machen 

1;

#########################################################################################################################################################
#########################################################################################################################################################
