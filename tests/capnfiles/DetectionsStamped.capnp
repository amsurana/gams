# Generated by ./generate_schemas.py. This file should not be modified by hand.
@0xe9dc8a23956109c4;

# Namespace setup
using Cxx = import "/capnp/c++.capnp";
$Cxx.namespace("gams::types");

# Capnfile Imports
using import "Detection.capnp".Detection;
using import "Header.capnp".Header;

# Type definition
struct DetectionsStamped {
   header @0: Header;
   detections @1: List(Detection);
   
}