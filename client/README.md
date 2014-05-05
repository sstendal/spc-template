# SPC Template client

Eksempel på en single-page frontend applikasjon.

Prosjektet er sjekket inn som en tutorial. Hvert steg kan sjekkes ut med:

	git checkout -f steg-<X>

F.eks:

	git checkout -f steg-1

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