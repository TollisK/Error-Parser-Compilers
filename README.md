# Error-Parser-Compilers
Implementation of an error parser for Java.

## Execution:
For the compilation of the program there is

    make
which creates the executable Main and to execute it is the command
"Java Main X" where X is all the java files we want to
perform. The debugger prints the corresponding message and then terminates.

## Structures:
To implement the Symbol Table we create a list of
class Symbol table, which is static in the MyVisitor class, for each class
which contains its name, a map for the variables of the class with
key name and item type and 2 lists of methods and from
class symbol table. Each method contains its name and type,
as well as 2 maps for its parameters and variables. While the
second map from Symbol table is used for the implementation of
classes that extend to this class. In the MyVisitor class there is
and a second list of Ext_class structures, consisting of 2 strings for
class names and the extended class, which helps
child class detection.

## Generally:
In main runs the MyVisitor class 2 times for each file with
different arguments, the first for creating the Symbol Table while the
second performs the checking. Finally, after the 2 calls are over
prints the offsets for each class.


