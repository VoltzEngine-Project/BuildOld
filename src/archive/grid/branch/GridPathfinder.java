package com.builtbroken.mc.lib.grid.branch;

import net.minecraftforge.common.util.ForgeDirection;
import com.builtbroken.mc.lib.grid.branch.part.Branch;
import com.builtbroken.mc.lib.grid.branch.part.Junction;
import com.builtbroken.mc.lib.grid.branch.part.Part;

import java.util.*;

/**
 * Simple connection path finder that generates grid parts while pathing all routes from a single node.
 *
 * @author Darkguardsman, Calclavia
 */
public class GridPathfinder
{
    /**
     * Network that is being pathed
     */
    private BranchedGrid grid;

    /**
     * All parts created by the path finder
     */
    private Set<Part> parts = new HashSet<Part>();

    /**
     * Nodes that have already been pathed
     */
    private List<NodeBranchPart> pathNodes = new LinkedList<NodeBranchPart>();

    public GridPathfinder(BranchedGrid grid)
    {
        this.grid = grid;
    }

    /**
     * Starts the path finder to generate grid parts from a list of nodes
     *
     * @return list of NetworkParts
     */
    public Set<Part> generateParts()
    {
        NodeBranchPart firstNode = grid.getFirstNode();
        if (firstNode != null)
        {
            path(null, firstNode, null);
        }
        return parts;
    }

    /**
     * Triggers a pathfinding loop from the node through all its connections and those node's connections.
     * Does not end until all connections are plotted, and creates new NetworkParts when required
     *
     * @param part        - last part created, used to connect new parts to, can be null for first profile
     * @param currentNode - current node being pathed
     * @param side        - side we are pathing to from the node, can only be null for first profile
     */
    protected Part path(Part part, NodeBranchPart currentNode, ForgeDirection side)
    {
        Map<NodeBranchPart, ForgeDirection> connections = currentNode.getConnections();
        Part nextPart = null;
        pathNodes.add(currentNode);
        
        //More than two connections, wire is a junction connecting to several paths
        if (connections.size() > 2)
        {
            //If we have another junction point merge it into a single junction
            if (part instanceof Junction)
            {
                
                ((Junction) part).add(currentNode);
                nextPart = part;
            }
            //Connection new junction to last part
            else
            {
                
                //Create new junction
                nextPart = new Junction();
                nextPart.add(currentNode);

                //Set last branches
                if(part != null)
                {
                    ((Junction) nextPart).addConnection(part);
                    ((Branch) part).setConnectionB(nextPart);
                }
            }
        }//Wire is a path only connecting in two directions
        else if(connections.size() == 2)
        {
            
            //If the last part was a wire add this wire to it
            if (part instanceof Branch)
            {
                
                ((Branch) part).add(currentNode);
                nextPart = part;
            }
            else
            {
                
                //Create a new wire and connect it to old part
                nextPart = new Branch();
                ((Branch) nextPart).add(currentNode);

                //Connect if part was not null
                if (part instanceof Junction)
                {
                    ((Branch) nextPart).setConnectionA(part);
                    ((Junction) part).addConnection(nextPart);
                }
            }

        }
        else
        {
            
            //TODO node is a dead end we need to exclude it from our network
        }

        //Add part to list
        if(nextPart != null)
            parts.add(nextPart);

        //Loop threw all connection triggering path() on each instance of NetworkNode
        for (Map.Entry<NodeBranchPart, ForgeDirection> entry : connections.entrySet())
        {
            if (entry.getKey() != null)
            {
                if (!pathNodes.contains(entry.getKey()))
                {
                    path(nextPart, (NodeBranchPart) entry.getKey(), entry.getValue());
                }
            }
        }
        //End of the line, make sure branches are connected at both ends
        if(nextPart != null && !nextPart.hasMinimalConnections())
        {
            for (Map.Entry<NodeBranchPart, ForgeDirection> entry : connections.entrySet())
            {
                if (entry.getKey() != null && !nextPart.contains(entry.getKey()))
                {
                    Part p = entry.getKey().branch();
                    if(p != nextPart)
                    {
                        if(p instanceof Junction)
                        {
                            if(nextPart instanceof Junction)
                            {
                                nextPart.join(p);
                            }
                            else if(nextPart instanceof Branch)
                            {
                                ((Junction) p).addConnection(nextPart);
                                ((Branch) nextPart).setConnectionB(p);
                            }
                        }
                        else if(p instanceof Branch)
                        {
                            if(nextPart instanceof Junction)
                            {
                                ((Branch) p).setConnectionB(nextPart);
                                ((Junction) nextPart).addConnection(p);
                            }
                            else if(nextPart instanceof Branch)
                            {
                                if(p.hasMinimalConnections())
                                {
                                    //TODO create junction, split p into two branches
                                }
                                else
                                {
                                    nextPart.join(p);
                                }
                            }
                        }
                    }
                }
            }
        }
        return nextPart;
    }

    /**
     * Clears out the path finder's results taking it back to a clean state
     */
    public void reset()
    {
        this.parts.clear();
        this.pathNodes.clear();
    }
}
