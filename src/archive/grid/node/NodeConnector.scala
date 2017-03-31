package com.builtbroken.mc.lib.grid.node

import java.util.{Map => JMap, Set => JSet}
import com.builtbroken.mc.prefab.tile.module.TileModule
import net.minecraftforge.common.util.ForgeDirection
import com.builtbroken.mc.api.tile.ITileModuleProvider
import com.builtbroken.mc.api.tile.node.INodeConnector
import com.builtbroken.mc.lib.helper.wrapper.BitmaskWrapper._

import scala.collection.convert.wrapAll._
import scala.collection.mutable

/**
 * A node that can connect to other nodes.
 *
 * @param parent - The INodeProvider that contains this node.
 * @tparam A - The type of objects this node can connect to.
 * @author Darkguardsman, Calclavia
 */
abstract class NodeConnector[A <: AnyRef](parent: ITileModuleProvider) extends TileModule(parent) with INodeConnector[A]
{
  /** The bitmask containing sides that this node may connect to */
  var connectionMask = 0x3F

  /** The bitmask containing the connected sides */
  protected var _connectedMask = 0x00

  def connectedMask = _connectedMask

  /** Functional event handler when the connection changes */
  var onConnectionChanged: () => Unit = () => ()

  var isInvalid = false

  /**
   * Connections to other nodes specifically.
   */
  private val connectionMap = mutable.WeakHashMap.empty[A, ForgeDirection]

  /**
   * Can this node connect with another node?
   * @param other - Most likely a node, but it can also be another object
   * @param from - Direction of connection
   * @return True connection is allowed
   */
  override def canConnect[B <: A](other: B, from: ForgeDirection): Boolean = isValidConnection(other) && canConnect(from)

  def canConnect(from: ForgeDirection): Boolean = connectionMask.mask(from) || from == ForgeDirection.UNKNOWN

  //TODO: This getClass.isAssignableFrom has issues.
  def isValidConnection(other: AnyRef): Boolean = other != null //&& other.getClass.isAssignableFrom(getClass)

  def connect[B <: A](obj: B, dir: ForgeDirection)
  {
    connectionMap.put(obj, dir)
    _connectedMask = _connectedMask.openMask(dir)
  }

  def disconnect[B <: A](obj: B)
  {
    connectionMap.remove(obj) match
    {
      case Some(x) => _connectedMask = _connectedMask.closeMask(x)
      case _ =>
    }
  }

  def disconnect(dir: ForgeDirection)
  {
    if (connectionMap.removeAll(connectionMap.filter(_._2 == dir)))
    {
      _connectedMask = _connectedMask.closeMask(dir)
    }
  }

  def clearConnections()
  {
    connectionMap.clear()
    _connectedMask = 0x00
  }

  override def connections: JSet[A] = connectionMap.keys.toSet[A]

  def directionMap: JMap[A, ForgeDirection] = connectionMap

  /**
   * Called to rebuild the connection map.
   * This is a general way used to search all adjacent TileEntity and attempt a connection
   */
  override def onJoinWorld()
  {
    super.onJoinWorld()
    val prevCon = connectedMask
    clearConnections()
    rebuild()

    if (prevCon != connectedMask)
      onConnectionChanged()

    isInvalid = false
  }

  def rebuild()
  {

  }

  override def onLeaveWorld()
  {
    super.onLeaveWorld()
    clearConnections()
    isInvalid = true
  }

  override def toString: String = getClass.getSimpleName + "[Connections: " + connections.size + "]"
}