/*********************************************************************
 * Copyright (c) 2013-2015 Carnegie Mellon University. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following acknowledgments and disclaimers.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. The names "Carnegie Mellon University," "SEI" and/or
 * "Software Engineering Institute" shall not be used to endorse or promote
 * products derived from this software without prior written permission. For
 * written permission, please contact permission@sei.cmu.edu.
 *
 * 4. Products derived from this software may not be called "SEI" nor may "SEI"
 * appear in their names without prior written permission of
 * permission@sei.cmu.edu.
 *
 * 5. Redistributions of any form whatsoever must retain the following
 * acknowledgment:
 *
 * This material is based upon work funded and supported by the Department of
 * Defense under Contract No. FA8721-05-C-0003 with Carnegie Mellon University
 * for the operation of the Software Engineering Institute, a federally funded
 * research and development center. Any opinions, findings and conclusions or
 * recommendations expressed in this material are those of the author(s) and
 * do not necessarily reflect the views of the United States Department of
 * Defense.
 *
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING
 * INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON
 * UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED,
 * AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR
 * PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE
 * MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND
 * WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 *
 * This material has been approved for public release and unlimited
 * distribution.
 *
 * @author James Edmondson <jedmondson@gmail.com>
 *********************************************************************/
package ai.gams.platforms;

import ai.gams.exceptions.GamsDeadObjectException;
import ai.gams.utility.Axes;
import ai.gams.utility.Position;
import ai.madara.exceptions.MadaraDeadObjectException;

/**
 * Interface for defining a platform to be used by GAMS. Care must be taken
 * to make all methods non-blocking, to prevent locking up the underlying
 * MADARA context.
 */
public interface PlatformInterface
{
  /**
   * Analyzes the platform. This should be
   * a non-blocking call.
   * @return   status information (@see PlatformStatusEnum)
   **/
  public int analyze () throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Returns the accuracy in meters
   * @return the accuracy of the platform
   **/
  public double getAccuracy () throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Returns the position accuracy in meters
   * @return position accuracy
   **/
  public double getPositionAccuracy () throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Returns the current position
   * @return the current position of the device/agent
   **/
  public Position getPosition () throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Returns to the home location. This should be
   * a non-blocking call.
   * @return   status information (@see PlatformReturnStatusEnum)
   **/
  public int home () throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Requests the platform to land. This should be
   * a non-blocking call.
   * @return   status information (@see PlatformReturnStatusEnum)
   **/
  public int land () throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Initializes a move to the target position. This should be
   * a non-blocking call.
   * @param   target     the new position to move to
   * @param   proximity  the minimum distance between current position
   *                   and target position that terminates the move.
   * @return  status information (@see PlatformReturnStatusEnum)
   **/
  public int move (Position target, double proximity) throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Initializes a rotate along x, y, z axes. This should be
   * a non-blocking call and implements an extrinsic rotation.
   * @param   axes       parameters for rotation along x, y, z axes
   * @return  status information (@see PlatformReturnStatusEnum)
   **/
  public int rotate (Axes axes) throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Get sensor radius
   * @return minimum radius of all available sensors for this platform
 * @throws MadaraDeadObjectException
   */
  public double getMinSensorRange () throws GamsDeadObjectException, MadaraDeadObjectException;

  /**
   * Gets the movement speed
   * @return movement speed
 * @throws MadaraDeadObjectException
   **/
  public double getMoveSpeed () throws GamsDeadObjectException, MadaraDeadObjectException;

  /**
   * Gets the unique id of the platform. This should be an alphanumeric
   * id that can be part of a MADARA variable name. Specifically, this
   * is used in the variable expansion of .platform.{yourid}.*
   * @return the id of the platform (alphanumeric only: no spaces!)
   **/
  public java.lang.String getId ();

  /**
   * Gets the name of the platform
   * @return the name of the platform
   **/
  public java.lang.String getName ();

  /**
   * Gets results from the platform's sensors. This should be
   * a non-blocking call.
   * @return  the status of the platform, @see PlatformStatusEnum
 * @throws GamsDeadObjectException
 * @throws MadaraDeadObjectException
   **/
  public int sense () throws GamsDeadObjectException, MadaraDeadObjectException;

  /**
   * Sets move speed
   * @param speed new speed in meters/second
 * @throws MadaraDeadObjectException
   **/
  public void setMoveSpeed (double speed) throws MadaraDeadObjectException;

  /**
   * Takes off. This should be
   * a non-blocking call.
   * @return   status information (@see PlatformReturnStatusEnum)
 * @throws GamsDeadObjectException
 * @throws MadaraDeadObjectException
   **/
  public int takeoff () throws MadaraDeadObjectException, GamsDeadObjectException;

  /**
   * Stops moving
 * @throws GamsDeadObjectException
 * @throws MadaraDeadObjectException
   **/
  public void stopMove () throws MadaraDeadObjectException, GamsDeadObjectException;
}

