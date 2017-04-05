package com.builtbroken.test.transform.region;

import com.builtbroken.mc.imp.transform.region.Cube;
import com.builtbroken.mc.imp.transform.vector.Pos;
import junit.framework.TestCase;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by robert on 12/14/2014.
 */
public class CuboidTest extends TestCase
{
    private Cube cube;

    @Override
    protected void setUp()
    {
        cube = new Cube(new Pos(-0.5, -0.5, -0.5), new Pos(0.5, 0.5, 0.5));
    }

    public void testXOverlap()
    {
        assertEquals("Failed overlap check one", true, cube.isOutSideX(2, 2));
        assertEquals("Failed overlap check two", false, cube.isOutSideX(-0.5, 0.5));
        assertEquals("Failed overlap check three", true, cube.isOutSideX(-2, 2));
    }

    public void testYOverlap()
    {
        assertEquals("Failed overlap check one", true, cube.isOutSideY(2, 2));
        assertEquals("Failed overlap check two", false, cube.isOutSideY(-0.5, 0.5));
        assertEquals("Failed overlap check three", true, cube.isOutSideY(-2, 2));
    }

    public void testZOverlap()
    {
        assertEquals("Failed overlap check one", true, cube.isOutSideZ(2, 2));
        assertEquals("Failed overlap check two", false, cube.isOutSideZ(-0.5, 0.5));
        assertEquals("Failed overlap check three", true, cube.isOutSideZ(-2, 2));
    }

    public void testCollisionOverlap()
    {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        {
            Pos vec = new Pos(dir).multiply(0.3);
            Cube c = cube.add(vec);
            if (!cube.doesOverlap(c))
            {
                System.out.println("Cube:  " + cube);
                System.out.println("Above: " + c.toString());
                assertEquals("x check failed", cube.isOutSideX(c.min().x(), c.max().x()), dir == ForgeDirection.EAST || dir == ForgeDirection.WEST);
                assertEquals("y check failed", cube.isOutSideY(c.min().y(), c.max().y()), dir == ForgeDirection.DOWN || dir == ForgeDirection.UP);
                assertEquals("z check failed", cube.isOutSideZ(c.min().z(), c.max().z()), dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH);
                fail("Didn't collide for side " + dir);
            }
        }
    }

    public void testCollisionCorners()
    {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        {
            for (int side = 0; side < 4; side++)
            {
                Pos vec = new Pos(dir).multiply(0.3);
                switch (dir)
                {
                    case DOWN:
                    case UP:
                        switch (side)
                        {
                            case 0:
                                vec.add(0.3, 0, 0.3);
                                break;
                            case 1:
                                vec.add(-0.3, 0, 0.3);
                                break;
                            case 2:
                                vec.add(0.3, 0, -0.3);
                                break;
                            case 3:
                                vec.add(-0.3, 0, -0.3);
                                break;
                        }
                    case NORTH:
                    case SOUTH:
                        switch (side)
                        {
                            case 0:
                                vec.add(0.3, 0.3, 0);
                                break;
                            case 1:
                                vec.add(-0.3, 0.3, 0);
                                break;
                            case 2:
                                vec.add(0.3, -0.3, 0);
                                break;
                            case 3:
                                vec.add(-0.3, -0.3, 0);
                                break;
                        }
                        break;
                    case WEST:
                    case EAST:
                        switch (side)
                        {
                            case 0:
                                vec.add(0, 0.3, 0.3);
                                break;
                            case 1:
                                vec.add(0, -0.3, 0.3);
                                break;
                            case 2:
                                vec.add(0, 0.3, -0.3);
                                break;
                            case 3:
                                vec.add(0, -0.3, -0.3);
                                break;
                        }
                        break;
                }

                Cube c = cube.clone().add(vec);
                if (!cube.doesOverlap(c))
                {
                    System.out.println("Cube:  " + cube);
                    System.out.println("Above: " + c.toString());
                    System.out.println("Is outside X limits -> " + cube.isOutSideX(c.min().x(), c.max().x()));
                    System.out.println("Is outside Y limits -> " + cube.isOutSideY(c.min().y(), c.max().y()));
                    System.out.println("Is outside Z limits -> " + cube.isOutSideZ(c.min().z(), c.max().z()));
                    fail("Didn't collide for side " + dir + " and corner " + side);
                }
            }
        }
    }

    public void testInsideBounds()
    {
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        {
            Cube above = cube.clone();
            assertEquals("Failed center collision check for side " + dir, true, cube.isInsideBounds(above));

            above = above.add(new Pos(dir));
            assertEquals("Failed collision check one for side " + dir, false, cube.isInsideBounds(above));

            above = above.add(new Pos(dir));
            assertEquals("Failed collision check two for side " + dir, false, cube.isInsideBounds(above));
        }
    }

}
