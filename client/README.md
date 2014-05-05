# SPC Template client

Eksempel på en single-page frontend applikasjon.

Prosjektet er sjekket inn som en tutorial. Hvert steg kan sjekkes ut med:

	git checkout -f steg-<X>

F.eks:

	git checkout -f steg-1

# Steg 7 - Ulike targets for bygging og intern webserver

Vi definerer to targets for connect og bruker en funksjon for å definere hvordan tasken 'serve' skal fungere.


# Steg 6 - minifying and concat

Nye plugins for minifying og sammenslåing av filer.

Vi bruker usemin plugin for å organisere dette. Den bruker igjen concat, cssmin og uglify.

## Bygging
Når du kjører grunt serve vil du se at alle css-filer og javascript-filer er slått sammen og minifisert.


# Steg 5 - Bower

Installerer Bower og laster ned jquery.

## bower.json
Denne filen definerer avhengigheter til eksterne javascript-bibliotek som brukes i nettsidene. Vi har lagt inn en avhengighet til jquery.

## Bygging
Last ned jquery med kommandoen

	bower install

Jquery legges under bower_components.

Kopier jquery.js til app/scripts med

	grunt bowercopy


# Steg 4 - Watch

Ny task som overvåker filer og laster websiden på nytt når noe endres.

## Bygging
Start webserveren med 

	grunt serve

Dette starter en jobb som overvåker alle endringer på filene og laster nettsiden på nytt ved endringer.


# Steg 3 - Node.js webserver

Ny task som åpner en Node.js webserver.

## Bygging
Etter at du har bygget prosjektet kan du starte en webserver med

	grunt connect


# Steg 2 - lage en tarball for installasjon

## package.json
Vi har lagt til fire dependencies i package.json.

### load-grunt-tasks
Brukes i Gruntfile.js for å forenkle lasting av tasks

### grunt-contrib-copy
Brukes for å kopiere filer fra katalogen app til dist

### grunt-contrib-compress
Brukes for å pakke filene i dist til en tarball

### grunt-contrib-clean
Brukes for å tømme bygge-katalogene

## Gruntfile.js
Gruntfile er utvidet med definisjoner som lager en distribusjons-fil under "public".

## Bygging
Bygg prosjektet med

	grunt

Tidligere bygginger slettes, alle filer kopieres på nytt og pakkes i filen public/spc-installation.tgz


# Steg 1 - initialisere prosjektet som et Grunt-prosjekt

Vi har lagt til to filer:

## package.js
Grunt bygger på Node.js. Filen package.js definerer dette som en Node.js pakke med navn, versjonsnummer og avhengigheter til andre Node.js pakker som vi vil bruke under byggingen.

## Gruntfile.js
Denne filen kjøres ved initialiseringen av Grunt. Vi starter med å definere en 'default' task som logger en enkelt melding.

## Bygging
Vi starter med å laste ned Grunt ved å kjøre Node Package Manager. Dette laster ned dependencies som er definert i package.json.

	npm install

Alle dependencies som lastes ned havner i node_modules. Denne katalogen skal ikke sjekkes inn i Git.

Nå kan du bygge prosjektet med grunt. 

	grunt


# Steg 0

Dette er foreløpig et rent html-prosjekt med en enkelt html-fil. 

## Bygging

Før du kan starte må du ha installert Node.js og npm (Node Package Manager) slik at du kan kjøre node og npm fra kommandolinjen.

Deretter må du installere grunt-cli globalt slik at du kan kjøre grunt fra kommandolinjen:

	npm install -g grunt-cli 