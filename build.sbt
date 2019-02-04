import org.nlogo.build.{ ExtensionDocumentationPlugin, NetLogoExtension }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

netLogoExtName := "fcmnetlogo"

name := "fcmnetlogo"

version := "0.1"

scalaVersion := "2.12.8"

netLogoZipSources   := false

version := "1.1.0"

netLogoClassManager := "FCMExtension"

netLogoTarget := NetLogoExtension.directoryTarget(baseDirectory.value)

netLogoVersion := "6.0.2-M1"

licenses  += ("Public Domain", url("http://creativecommons.org/licenses/publicdomain/"))
