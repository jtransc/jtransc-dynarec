# jtransc-dynarec
Experimental dynamic recompilation for JTransc

This is an experimental repository for trying dynamic recompilation on JTransc.
This would allow to create simple code dynamically in a way that works on JTransc.
A possible usage of this is fast emulators or code that need to generate simple functions on the fly. 
Generating javascript on the fly, jvm bytecode, libjit among others and including an interpreter for not supported targets.

The idea is to mark classes that can be used inside generated functions with an annotation,
so it is possible on some targets like C++ to generate required information at compile time using metaprogramming.
