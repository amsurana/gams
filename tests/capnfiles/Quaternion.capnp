# Generated by ./generate_schemas.py. This file should not be modified by hand.
@0xb0fe64e700c5aad4;

# Namespace setup
using Cxx = import "/capnp/c++.capnp";
$Cxx.namespace("gams::types");

# Capnfile Imports

# Type definition
struct Quaternion {
   y @0: Float64;
   x @1: Float64;
   z @2: Float64;
   w @3: Float64;
   
}