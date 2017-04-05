package com.builtbroken.mc.codegen.templates;

import com.builtbroken.mc.codegen.template.TileWrappedTemplate;
import com.builtbroken.mc.framework.logic.ITileNode;
import com.builtbroken.mc.framework.logic.wrapper.TileEntityWrapper;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/1/2017.
 */
@TileWrappedTemplate(annotationName = "Empty")
public class TileTemplate extends TileEntityWrapper
{
    public TileTemplate(ITileNode controller)
    {
        super(controller);
    }

    /*
        At the moment the parser is designed to be super simple.
        This means it needs help noting where methods start and
        where fields start. This way it can just copy both
        sections out, parse out all the entries, and store
        then for file generation.

        Future versions will move away from this concept to a
        full class parser matching java standards.
    */

    //#StartFields#
    public static final String TEST_FIELD = "DATA";
    //#EndFields#

    //#StartMethods#
    @Override
    public String getClassDisplayName()
    {
        return "TileEntityWrapper(Empty)";
    }
    //#EndMethods#
}
