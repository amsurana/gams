# Generated by ./generate_schemas.py. This file should not be modified by hand.
@0xae06ed7406e5e8b6;

# Namespace setup
using Cxx = import "/capnp/c++.capnp";
$Cxx.namespace("gams::types");

# Capnfile Imports
using import "Header.capnp".Header;
using import "Transform.capnp".Transform;

# Type definition
struct TransformStamped {
   header @0: Header;
   transform @1: Transform;
   childFrameId @2: Text;
   
}