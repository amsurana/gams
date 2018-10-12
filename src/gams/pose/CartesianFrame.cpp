/**
 * Copyright (c) 2015 Carnegie Mellon University. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following acknowledgments and disclaimers.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. The names "Carnegie Mellon University," "SEI" and/or "Software
 *    Engineering Institute" shall not be used to endorse or promote products
 *    derived from this software without prior written permission. For written
 *    permission, please contact permission@sei.cmu.edu.
 * 
 * 4. Products derived from this software may not be called "SEI" nor may "SEI"
 *    appear in their names without prior written permission of
 *    permission@sei.cmu.edu.
 * 
 * 5. Redistributions of any form whatsoever must retain the following
 *    acknowledgment:
 * 
 *      This material is based upon work funded and supported by the Department
 *      of Defense under Contract No. FA8721-05-C-0003 with Carnegie Mellon
 *      University for the operation of the Software Engineering Institute, a
 *      federally funded research and development center. Any opinions,
 *      findings and conclusions or recommendations expressed in this material
 *      are those of the author(s) and do not necessarily reflect the views of
 *      the United States Department of Defense.
 * 
 *      NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING
 *      INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON
 *      UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR
 *      IMPLIED, AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF
 *      FITNESS FOR PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS
 *      OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES
 *      NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM PATENT,
 *      TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 * 
 *      This material has been approved for public release and unlimited
 *      distribution.
 **/

#include "CartesianFrame.h"
#include "GPSFrame.h"
#include "UTMFrame.h"
#include "geodetic_utils/geodetic_conv.h"

namespace gams { namespace pose {
  namespace cartesian {

    double calc_distance(
                      const ReferenceFrameType * /*self*/,
                      double x1, double y1, double z1,
                      double x2, double y2, double z2)
    {
      double x_dist = x2 - x1;
      double y_dist = y2 - y1;
      double z_dist = z2 - z1;

      return sqrt(x_dist * x_dist + y_dist * y_dist + z_dist * z_dist);
    }

    void transform_linear_to_origin(
                      const ReferenceFrameType *origin,
                      const ReferenceFrameType *self,
                      double ox, double oy, double oz,
                      double orx, double ory, double orz,
                      double &x, double &y, double &z,
                      bool fixed)
    {
      if (origin->type_id == self->type_id) {
        simple_rotate::orient_linear_vec(x, y, z, orx, ory, orz);

        if (fixed) {
          x += ox;
          y += oy;
          z += oz;
        }
      } else if (origin->type_id == GPS->type_id) {
        simple_rotate::orient_linear_vec(x, y, z, orx, ory, orz);

        if (fixed) {
          geodetic_util::GeodeticConverter conv(ox, oy, oz);

          double lat, lon, alt;
          conv.ned2Geodetic(x, y, z, &lat, &lon, &alt);

          std::cerr << ox << " " << oy << " " << oz << " :: " <<
                       x << " " << y << " " << z << " -> " <<
                       lat << " " << lon << " " << alt << std::endl;

          x = lat;
          y = lon;
          z = alt;
        }

        self->normalize_linear(self, x, y, z);
      }
#if 0
#ifdef GAMS_UTM
      GAMS_WITH_FRAME_TYPE(origin(), UTMFrame, frame)
      {
        orient_linear_vec(x, y, z, origin());

        x += origin().x();
        y += origin().y();
        z += origin().z();
        return;
      }
#endif
#endif
      else {
        throw undefined_transform(self, origin, true);
      }
    }

    void transform_linear_from_origin(
                      const ReferenceFrameType *origin,
                      const ReferenceFrameType *self,
                      double ox, double oy, double oz,
                      double orx, double ory, double orz,
                      double &x, double &y, double &z,
                      bool fixed)
    {
      if (origin->type_id == self->type_id)
      {
        simple_rotate::orient_linear_vec(x, y, z, orx, ory, orz, true);

        if (fixed) {
          x -= ox;
          y -= oy;
          z -= oz;
        }
      } else if (origin->type_id == GPS->type_id) {
        self->normalize_linear(self, x, y, z);

        if (fixed) {
          geodetic_util::GeodeticConverter conv(ox, oy, oz);

          double north, east, down;
          conv.geodetic2Ned(x, y, z, &north, &east, &down);

          std::cerr << ox << " " << oy << " " << oz << " :: " <<
                       x << " " << y << " " << z << " -> " <<
                       north << " " << east << " " << down << std::endl;

          x = north;
          y = east;
          z = down;
        }

        simple_rotate::orient_linear_vec(x, y, z, orx, ory, orz, true);
      }
#if 0
#ifdef GAMS_UTM
      GAMS_WITH_FRAME_TYPE(origin(), UTMFrame, frame)
      {
        orient_linear_vec(x, y, z, origin(), true);

        x -= origin().x();
        y -= origin().y();
        z -= origin().z();
        return;
      }
#endif
#endif
      else {
        throw undefined_transform(self, origin, false);
      }
    }

    const ReferenceFrameType CartesianImpl = {
      1, "Cartesian",
      transform_linear_to_origin,
      transform_linear_from_origin,
      default_normalize_linear,
      calc_distance,
      simple_rotate::transform_angular_to_origin,
      simple_rotate::transform_angular_from_origin,
      default_normalize_angular,
      simple_rotate::calc_angle,
      simple_rotate::transform_pose_to_origin,
      simple_rotate::transform_pose_from_origin,
      default_normalize_pose,
    };
  } // End namespace cartesian

  const ReferenceFrameType *Cartesian = &cartesian::CartesianImpl;
} }
