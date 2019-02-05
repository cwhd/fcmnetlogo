# FCM NetLogo Extension

This [extension for NetLogo](http://ccl.northwestern.edu/netlogo/docs/extensions.html)
allows modellers to use Fuzzy Cognitive Maps (FCMs) in their Agent Based Models (ABM).
This project is a work in progress, check back soon for more updates!

## Abstract

[Agent Based Modelling (ABM)](https://en.wikipedia.org/wiki/Agent-based_model
) is used for mapping complex interactions between multiple entities
known as “agents” and their environment. However, where ABMs excel in mapping entities and their
interaction over time they are defined with procedural programming, which is an inadequate medium
for defining how humans and in theory how AI will react to its environment.

[Fuzzy Cognitive Maps (FCM)](https://en.wikipedia.org/wiki/Fuzzy_cognitive_map) are extremely good at modelling how humans make decisions but lack the
ability to be observed in a feedback loop over time. The combination of these two modelling techniques
is a great fit for modelling potential sociological systems where humans, robots, and AI interact in
the real world.

This NetLogo extension is intended to bridge the gap by bringing FCM to ABM through tbe very
popular NetLogo platform.

## Instructions

This is still a work in progress, but to get started you can package this as a jar with SBT. Once
you've done that put it in your NetLog extensions folder, it should look something like this:
C:\Program Files\NetLogo 6.0.4\app\extensions\fcmnetlogo

In NetLogo first declare that you're using the plugin like this:

```NetLogo
extensions [fcmnetlogo]
```

Then you can call it by referencing your model definition and then your inputs like this:

```NetLogo
  print "fcm1 example..."
  let fcm1 fcmnetlogo:callfcm "C:\\Program Files\\NetLogo 6.0.4\\app\\extensions\\fcmnetlogo\\PredatorAndPrey.xml" (list "Food" 0.3 "Prey" 1 "Predators" 0.1)
  print fcm1
```

This project uses [JFCM](https://github.com/megadix/jfcm
) for now, so FCM formats need to be in XML like [these](https://github.com/megadix/jfcm-samples/tree/master/src/main/resources/org/megadix/jfcm/samples)

Currently when you print your results you'll just get a list of numbers. Ideally when this is done
you should get back iteration information as well as the final results in a map.

## Terms of Use

[![CC0](http://i.creativecommons.org/p/zero/1.0/88x31.png)](http://creativecommons.org/publicdomain/zero/1.0/)

The NetLogo FCM Extension is in the public domain.  To the extent possible under law, Uri Wilensky has waived all copyright and related or neighboring rights for NetLogo.