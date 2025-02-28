# GenProtobuf

[GenProtobuf on Jetbrains Marketplace](https://plugins.jetbrains.com/plugin/11423-genprotobuf/edit)

The GenProtobuf plugin for intellij IDEs

I built this plugin to simplify protobuf compilation for Computer Engineering students I was teaching at the time. It adds convenient right click menus to compile protobuf files within Intellij IDEs. It has since been updated to support newer Intellij IDE versions and allow for use with languages that are supported by plugins.

## Generate code from .proto files (they must have a .proto extension).

GenProtobuf adds two right-click menu entries "quick gen protobuf here" and "quick gen protobuf rules" to the project area and "Configure GenProtobuf" and "Generate all Protobufs" to the tools menu.

`quick gen protobuf here` will quickly generate code for selected protobuf files in a single selected language as configured under "Configure GenProtobuf" in the tools menu, placing the output in the same directory as the selected protobuf files.

`quick gen protobuf rules` will generate code for selected protobuf files according to the rules set with "Configure GenProtobuf" in the tools menu and place the output in the configured output location.

Finally, you can use `Generate all Protobufs` under the tools menu, and it will apply the rules set in `Configure GenProtobuf` (again under the tools menu) to all the protobuf files it finds in your project.

As stated in the descriptions above, you can configure this plugin from the `Configure GenProtobuf` menu that gets added to the tools menu. You are able to set what programming language to use for the quick gen options and toggle different languages to be generated for protobuf files, as well as different directories for the various language outputs.

Whenever you generate code from a .proto file, a tool window is created to display the resulting textual output.

* version 0.1 - starts to exist
* version 1.0 - initial plugin upload
* version 1.1 - changed plugin.xml to enable all IntelliJ products
* version 1.2 - Converted use of DataKeys to LangDataKeys so quick actions work in IDEs other than IDEA
    * Tested to work in Pycharm and IDEA ref:
        * https://intellij-support.jetbrains.com/hc/en-us/community/posts/206767135-DataKeys-class-not-found-in-PhpStorm
* version 1.3 - Who releases 4 versions of a plugin in 2 days? Apparently me, if you want to go fast go slow... Version 1.2 works fine but I forgot to update the value of the quick language selection combo box so it defaults to Python every time you open up the configuration menu. In 1.3 this issue is resolved and I have run out of known issues and used it on PyCharm and IDEA.
* version 1.4 - Altered the way output is displayed slightly to remove use of deprecated APIs.
* version 1.5 - Added support for custom languages that require plugins (like Go and Dart)
* version 2.0 - updated use of deprecated interface. Restarting compatibility with 2022.2.1 use 1.5.1 with older versions it is identical in functionality.
* version 2.0.1 - updated plugin.xml to support versions newer than 222.*
* version 2.0.2 - updated metadata
