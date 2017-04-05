package com.builtbroken.mc.core.asm;

import com.builtbroken.mc.core.EngineCoreMod;
import com.builtbroken.mc.lib.asm.ASMHelper;
import com.builtbroken.mc.lib.asm.ObfMapping;
import net.minecraft.block.Block;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/** {@link net.minecraft.world.chunk.Chunk#func_150807_a(int, int, int, Block, int)}
 * @author Calclavia
 */
public class ChunkTransformer implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes)
	{
		if (transformedName.equals("net.minecraft.world.chunk.Chunk"))
		{
			EngineCoreMod.logger.info("Transforming Chunk class for chunkModified event.");

			ClassNode cnode = ASMHelper.createClassNode(bytes);

			for (MethodNode method : cnode.methods)
			{
				ObfMapping m = new ObfMapping(cnode.name, method.name, method.desc).toRuntime();

				if (m.s_name.equals("func_150807_a"))
				{
					EngineCoreMod.logger.info("[Voltz-Engine] Found method " + m.s_name);
					InsnList list = new InsnList();
					list.add(new VarInsnNode(ALOAD, 0));
					list.add(new VarInsnNode(ILOAD, 1));
					list.add(new VarInsnNode(ILOAD, 2));
					list.add(new VarInsnNode(ILOAD, 3));
					list.add(new VarInsnNode(ALOAD, 4));
					list.add(new VarInsnNode(ILOAD, 5));
					list.add(new MethodInsnNode(INVOKESTATIC, "com/builtbroken/mc/core/asm/StaticForwarder", "chunkSetBlockEvent", "(Lnet/minecraft/world/chunk/Chunk;IIILnet/minecraft/block/Block;I)V"));

					AbstractInsnNode lastInsn = method.instructions.getLast();
					while (lastInsn instanceof LabelNode || lastInsn instanceof LineNumberNode)
					{
						lastInsn = lastInsn.getPrevious();
					}

					if (isReturn(lastInsn))
					{
						method.instructions.insertBefore(lastInsn, list);
					}
					else
					{
						method.instructions.insert(list);
					}

					EngineCoreMod.logger.info("Injected instruction to method: " + m.s_name);
				}
			}

			return ASMHelper.createBytes(cnode, 0);
		}

		return bytes;
	}

	private boolean isReturn(AbstractInsnNode node)
	{
		switch (node.getOpcode())
		{
			case RET:
			case RETURN:
			case ARETURN:
			case DRETURN:
			case FRETURN:
			case IRETURN:
			case LRETURN:
				return true;

			default:
				return false;
		}
	}

}