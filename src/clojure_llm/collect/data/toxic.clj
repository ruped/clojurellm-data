(ns clojure-llm.collect.data.toxic
  (:import (java.util.regex Pattern)))

; https://raw.githubusercontent.com/LDNOOBW/List-of-Dirty-Naughty-Obscene-and-Otherwise-Bad-Words/master/en
(def bad-names1
  "2g1c
2 girls 1 cup
acrotomophilia
alabama hot pocket
alaskan pipeline
anal
anilingus
anus
apeshit
arsehole
asshole
assmunch
auto erotic
autoerotic
babeland
baby batter
baby juice
ball gag
ball gravy
ball kicking
ball licking
ball sack
ball sucking
bangbros
bangbus
bareback
barely legal
barenaked
bastard
bastardo
bastinado
bbw
bdsm
beaner
beaners
beaver cleaver
beaver lips
beastiality
bestiality
big black
big breasts
big knockers
big tits
bimbos
birdlock
bitch
bitches
black cock
blonde action
blonde on blonde action
blowjob
blow job
blow your load
blue waffle
blumpkin
bollocks
bondage
boner
boob
boobs
booty call
brown showers
brunette action
bukkake
bulldyke
bullet vibe
bullshit
bung hole
bunghole
busty
buttcheeks
butthole
camel toe
camgirl
camslut
camwhore
carpet muncher
carpetmuncher
chocolate rosebuds
cialis
circlejerk
cleveland steamer
clit
clitoris
clover clamps
clusterfuck
cock
cocks
coprolagnia
coprophilia
cornhole
coon
coons
creampie
cumming
cumshot
cumshots
cunnilingus
cunt
darkie
date rape
daterape
deep throat
deepthroat
dendrophilia
dick
dildo
dingleberry
dingleberries
dirty pillows
dirty sanchez
doggie style
doggiestyle
doggy style
doggystyle
dog style
dolcett
dominatrix
dommes
donkey punch
double dong
double penetration
dp action
dry hump
dvda
eat my ass
ecchi
ejaculation
erotic
erotism
escort
eunuch
fag
faggot
fecal
felch
fellatio
feltch
female squirting
femdom
figging
fingerbang
fingering
fisting
foot fetish
footjob
frotting
fuck
fuck buttons
fuckin
fucking
fucktards
fudge packer
fudgepacker
futanari
gangbang
gang bang
gay sex
genitals
giant cock
girl on
girl on top
girls gone wild
goatcx
goatse
god damn
gokkun
golden shower
goodpoop
goo girl
goregasm
grope
group sex
g-spot
guro
hand job
handjob
hentai
homoerotic
honkey
hooker
horny
hot carl
hot chick
how to kill
how to murder
huge fat
humping
incest
intercourse
jack off
jail bait
jailbait
jelly donut
jerk off
jigaboo
jiggaboo
jiggerboo
jizz
juggs
kike
kinbaku
kinkster
kinky
knobbing
leather restraint
leather straight jacket
lemon party
livesex
lolita
lovemaking
make me come
male squirting
masturbate
masturbating
masturbation
menage a trois
milf
missionary position
mong
motherfucker
mound of venus
mr hands
muff diver
muffdiving
nambla
nawashi
negro
neonazi
nigga
nigger
nig nog
nimphomania
nipple
nipples
nsfw
nsfw images
nude
nudity
nutten
nympho
nymphomania
octopussy
omorashi
one cup two girls
one guy one jar
orgasm
orgy
paedophile
paki
panties
panty
pedobear
pedophile
penis
phone sex
piece of shit
pikey
pissing
piss pig
pisspig
playboy
pleasure chest
pole smoker
ponyplay
poof
poon
poontang
punany
poop chute
poopchute
porn
porno
pornography
prince albert piercing
pthc
pubes
pussy
queaf
queef
quim
raghead
raging boner
rape
raping
rapist
rectum
reverse cowgirl
rimjob
rimming
rosy palm
rosy palm and her 5 sisters
rusty trombone
sadism
santorum
schlong
scissoring
semen
sexcam
sesexy
shaved beaver
shaved pussy
shemale
shibari
shit
shitblimp
shitty
shota
shrimping
skeet
slanteye
slut
s&m
smut
snatch
snowballing
sodomize
sodomy
spastic
spic
splooge
splooge moose
spooge
spread legs
spunk
strap on
strapon
strappado
strip club
style doggy
suicide girls
sultry women
swastika
swinger
tainted love
taste my
tea bagging
threesome
throating
thumbzilla
tied up
tight white
tit
tits
titties
titty
tongue in a
topless
tosser
towelhead
tranny
tribadism
tub girl
tubgirl
tushy
twat
twink
twinkie
two girls one cup
undressing
upskirt
urethra play
urophilia
vagina
venus mound
viagra
vibrator
violet wand
vorarephilia
voyeur
voyeurweb
voyuer
vulva
wank
wetback
wet dream
white power
whore
worldsex
wrapping men
wrinkled starfish
yaoi
yellow showers
yiffy
zoophilia
🖕")

; https://gist.githubusercontent.com/ryanlewis/a37739d710ccdb4b406d/raw/0fbd315eb2900bb736609ea894b9bde8217b991a/google_twunter_lol
(def bad-names2
  "4r5e
5h1t
5hit
a55
anal
anus
ar5e
arrse
arse
ass-fucker
assfucker
assfukka
asshole
assholes
asswhole
a_s_s
b!tch
b00bs
b17ch
b1tch
ballbag
ballsack
bastard
beastial
beastiality
bellend
bestial
bestiality
bi+ch
biatch
bitch
bitcher
bitchers
bitches
bitchin
bitchin
blow job
blowjob
blowjobs
boiolas
bollock
bollok
boner
boob
boobs
booobs
boooobs
booooobs
booooooobs
breasts
buceta
bugger
bum
bunny fucker
butthole
buttmuch
buttplug
c0ck
c0cksucker
carpet muncher
cawk
chink
cipa
cl1t
clit
clitoris
clits
cnut
cock
cock-sucker
cockface
cockhead
cockmunch
cockmuncher
cocks
cocksuck 
cocksucked 
cocksucker
cocksucking
cocksucks 
cocksuka
cocksukka
cok
cokmuncher
coksucka
coon
cox
cummer
cumming
cums
cumshot
cunilingus
cunillingus
cunnilingus
cunt
cuntlick 
cuntlicker 
cuntlicking 
cunts
cyalis
cyberfuc
cyberfuck 
cyberfucked 
cyberfucker
cyberfuckers
cyberfucking 
d1ck
dick
dickhead
dildo
dildos
dink
dinks
dirsa
dlck
dog-fucker
doggin
dogging
donkeyribber
doosh
duche
dyke
ejaculate
ejaculated
ejaculates 
ejaculating 
ejaculatings
ejaculation
ejakulate
f u c k
f u c k e r
f4nny
fag
fagging
faggitt
faggot
faggs
fagot
fagots
fags
fanny
fannyflaps
fannyfucker
fanyy
fatass
fcuk
fcuker
fcuking
feck
fecker
felching
fellate
fellatio
fingerfuck 
fingerfucked 
fingerfucker 
fingerfuckers
fingerfucking 
fingerfucks 
fistfuck
fistfucked 
fistfucker 
fistfuckers 
fistfucking 
fistfuckings 
fistfucks 
flange
fook
fooker
fuck
fucka
fucked
fucker
fuckers
fuckhead
fuckheads
fuckin
fucking
fuckings
fuckingshitmotherfucker
fuckme 
fucks
fuckwhit
fuckwit
fudge packer
fudgepacker
fuk
fuker
fukker
fukkin
fuks
fukwhit
fukwit
fux
fux0r
f_u_c_k
gangbang
gangbanged 
gangbangs 
gaylord
gaysex
goatse
god-dam
god-damned
goddamn
goddamned
hardcoresex 
heshe
hoar
hoare
hoer
homo
hore
horniest
horny
hotsex
jack-off 
jackoff
jap
jerk-off 
jism
jiz 
jizm 
jizz
kawk
knobead
knobed
knobend
knobhead
knobjocky
knobjokey
kock
kondum
kondums
kum
kummer
kumming
kums
kunilingus
l3i+ch
l3itch
labia
lmfao
lusting
m0f0
m0fo
m45terbate
ma5terb8
ma5terbate
masochist
master-bate
masterb8
masterbat*
masterbat3
masterbate
masterbation
masterbations
masturbate
mo-fo
mof0
mofo
mothafuck
mothafucka
mothafuckas
mothafuckaz
mothafucked 
mothafucker
mothafuckers
mothafuckin
mothafucking 
mothafuckings
mothafucks
mother fucker
motherfuck
motherfucked
motherfucker
motherfuckers
motherfuckin
motherfucking
motherfuckings
motherfuckka
motherfucks
muff
mutha
muthafecker
muthafuckker
muther
mutherfucker
n1gga
n1gger
nazi
nigg3r
nigg4h
nigga
niggah
niggas
niggaz
nigger
niggers 
nob
nob jokey
nobhead
nobjocky
nobjokey
numbnuts
nutsack
orgasim 
orgasims 
orgasm
orgasms 
p0rn
pecker
penis
penisfucker
phonesex
phuck
phuk
phuked
phuking
phukked
phukking
phuks
phuq
pigfucker
pimpis
piss
pissed
pisser
pissers
pisses 
pissflaps
pissin 
pissing
pissoff 
poop
porn
porno
pornography
pornos
prick
pricks 
pube
pusse
pussi
pussies
pussy
pussys 
rectum
retard
rimjaw
rimming
s hit
s.o.b.
sadist
schlong
scroat
scrote
scrotum
semen
sh!+
sh!t
sh1t
shag
shagger
shaggin
shagging
shemale
shi+
shit
shitdick
shite
shited
shitey
shitfuck
shitfull
shithead
shiting
shitings
shits
shitted
shitter
shitters 
shitting
shittings
shitty 
skank
slut
sluts
smegma
smut
snatch
son-of-a-bitch
spac
spunk
s_h_i_t
t1tt1e5
t1tties
teets
teez
testical
testicle
tit
titfuck
tits
titt
tittie5
tittiefucker
titties
tittyfuck
tittywank
titwank
tosser
tw4t
twat
twathead
twatty
twunt
twunter
v14gra
v1gra
vagina
viagra
vulva
w00se
wang
wank
wanker
wanky
whoar
whore
willies
willy
xrated")

; https://gist.githubusercontent.com/aldeka/3456141/raw/4ab92f39c4b0258eedd2a72252bdada6e3593104/Bad%2520words
(def bad-names3
  "4r5e, 5h1t, 5hit, a55, anal, anus, ar5e, arrse, arse, ass-fucker, assfucker, assfukka, asshole, assholes, asswhole, a_s_s, b!tch, b00bs, b17ch, b1tch, ballbag, ballsack, bastard, beastial, beastiality, bellend, bestial, bestiality, bi+ch, biatch, bitch, bitcher, bitchers, bitches, bitchin, bitching , blow job, blowjob, blowjobs, boiolas, bollock, bollok, boner, boob, boobs, booobs, boooobs, booooobs, booooooobs, breasts, buceta, bugger, bum, bunny fucker, butthole, buttmuch, buttplug, c0ck, c0cksucker, carpet muncher, cawk, chink, cipa, cl1t, clit, clitoris, clits, cnut, cock, cock-sucker, cockface, cockhead, cockmunch, cockmuncher, cocks, cocksuck , cocksucked , cocksucker, cocksucking, cocksucks , cocksuka, cocksukka, cok, cokmuncher, coksucka, coon, cox, cummer, cumming, cums, cumshot, cunilingus, cunillingus, cunnilingus, cunt, cuntlick , cuntlicker , cuntlicking , cunts, cyalis, cyberfuc, cyberfuck , cyberfucked , cyberfucker, cyberfuckers, cyberfucking , d1ck , dick, dickhead, dildo, dildos, dink, dinks, dirsa, dlck, dog-fucker, doggin, dogging, donkeyribber, doosh, duche, dyke, ejaculate, ejaculated, ejaculates , ejaculating , ejaculatings, ejaculation, ejakulate, f u c k, f u c k e r, f4nny, fag, fagging, faggitt, faggot, faggs, fagot, fagots, fags, fanny, fannyflaps, fannyfucker, fanyy, fatass, fcuk, fcuker, fcuking, feck, fecker, felching, fellate, fellatio, fingerfuck , fingerfucked , fingerfucker , fingerfuckers, fingerfucking , fingerfucks , fistfuck, fistfucked , fistfucker , fistfuckers , fistfucking , fistfuckings , fistfucks , flange, fook, fooker, fuck, fucka, fucked, fucker, fuckers, fuckhead, fuckheads, fuckin, fucking, fuckings, fuckingshitmotherfucker, fuckme , fucks, fuckwhit, fuckwit, fudge packer, fudgepacker, fuk, fuker, fukker, fukkin, fuks, fukwhit, fukwit, fux, fux0r, f_u_c_k, gangbang, gangbanged , gangbangs , gaylord, gaysex, goatse, god-dam, god-damned, goddamn, goddamned, hardcoresex , heshe, hoar, hoare, hoer, homo, hore, horniest, horny, hotsex, jack-off , jackoff, jap, jerk-off , jism, jiz , jizm , jizz, kawk, knobead, knobed, knobend, knobhead, knobjocky, knobjokey, kock, kondum, kondums, kum, kummer, kumming, kums, kunilingus, l3i+ch, l3itch, labia, lmfao, lusting, m0f0, m0fo, m45terbate, ma5terb8, ma5terbate, masochist, master-bate, masterb8, masterbat*, masterbat3, masterbate, masterbation, masterbations, masturbate, mo-fo, mof0, mofo, mothafuck, mothafucka, mothafuckas, mothafuckaz, mothafucked , mothafucker, mothafuckers, mothafuckin, mothafucking , mothafuckings, mothafucks, mother fucker, motherfuck, motherfucked, motherfucker, motherfuckers, motherfuckin, motherfucking, motherfuckings, motherfuckka, motherfucks, muff, mutha, muthafecker, muthafuckker, muther, mutherfucker, n1gga, n1gger, nazi, nigg3r, nigg4h, nigga, niggah, niggas, niggaz, nigger, niggers , nob, nob jokey, nobhead, nobjocky, nobjokey, numbnuts, nutsack, orgasim , orgasims , orgasm, orgasms , p0rn, pecker, penis, penisfucker, phonesex, phuck, phuk, phuked, phuking, phukked, phukking, phuks, phuq, pigfucker, pimpis, piss, pissed, pisser, pissers, pisses , pissflaps, pissin , pissing, pissoff , poop, porn, porno, pornography, pornos, prick, pricks, pube, pusse, pussi, pussies, pussy, pussys , rectum, retard, rimjaw, rimming, s hit, s.o.b., sadist, schlong, scroat, scrote, scrotum, seme, sh!+, sh!t, sh1t, shag, shagger, shaggin, shagging, shemale, shi+, shit, shitdick, shite, shited, shitey, shitfuck, shitfull, shithead, shiting, shitings, shits, shitted, shitter, shitters , shitting, shittings, shitty , skank, slut, sluts, smegma, smut, snatch, son-of-a-bitch, spac, spunk, s_h_i_t, t1tt1e5, t1tties, teets, teez, testical, testicle, tit, titfuck, tits, titt, tittie5, tittiefucker, titties, tittyfuck, tittywank, titwank, tosser, tw4t, twat, twathead, twatty, twunt, twunter, v14gra, v1gra, vagina, viagra, vulva, w00se, wang, wank, wanker, wanky, whoar, whore, willies, xrated")

(defn get-non-space-comma-tokens [s]
  (->> s
       (partition-by (complement #{#_\space \newline \,}))
       (filter (complement #(or #_(= \space (first %))
                             (= \, (first %))
                                (= \newline (first %)))))
       (map #(apply str %))))

(def bad-names
  (->> [bad-names1 bad-names2 bad-names3]
       (map get-non-space-comma-tokens)
       (apply concat)
       (filter #(< 2 (count %)))
       set))

;; (count bad-names)
;; (take 10 (sort bad-names))

(defn get-non-space-tokens [s]
  (->> s
       (partition-by (complement #{\space \newline #_#_#_#_#_#_#_#_\" \, \. \' \( \) \@ \<}))
       (filter (complement #(= \space (first %))))
       (map #(apply str %))))

(def toxic-patterns
  (->> bad-names
       (mapv #(Pattern/compile (str "(" % ")")))))
#_(take 10 (sort bad-names))

;; (defn toxic? [body]
;;   (seq (filter bad-names (get-non-space-tokens body)))

;;   #_
;;   (let [txs (filter #(re-find % body) toxic-patterns)]
;;     (println :toxics txs)
;;     (boolean (first txs))) )

(defn toxic? [body]
  (seq (filter bad-names (get-non-space-tokens body))))

#_#_
(seq (filter bad-names (get-non-space-tokens "well, you know what? dag nam sum biatch. fuck ass mofo ass")))
(toxic? "well, you know what? dag nam sum biatch. fuck ass mofo ass")

