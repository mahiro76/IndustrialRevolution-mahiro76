package mahiro76.mahiro;

import mahiro76.mahiro.registry.*;
import mahiro76.mahiro.world.gen.MahiroWorldGen;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mahiro implements ModInitializer {
	/**
	 * 此记录器用于将文本写入控制台和日志文件。
	 * 最好使用 mod id 作为 logger 的名称，
	 * 这样可以清楚地知道哪个 Mod 编写了信息、警告和错误。
	 */
	public static final String MOD_ID = "mahiro";
	public static final Logger LOGGER = LoggerFactory.getLogger("Mahiro");
	/**
	 * 此方法{@link #onInitialize()}会在 Minecraft 进入 mod-load-ready 状态后立即执行。
	 * 但需注意，此时部分内容（如资源）可能尚未初始化，请谨慎操作。
	 *
	 * <p>如果不熟悉 MIXIN，可以通过如下方式让物品可用作燃料（Fabric API），
	 * 注意必须写在模组主类中：
	 * <pre><code>
	 * public void onInitialize() {
	 *     FuelRegistry.INSTANCE.add(MahiroItems.CRUTCH, 300);
	 * }
	 * </code></pre>
	 */
	@Override
	public void onInitialize() {
		LOGGER.info("mahiro run successfully!");
		MahiroItems.registerMahiroItems();
		MahiroItemGroup.registerModItemGroup();
		MahiroBlocks.registerMahiroBlocks();
		MahiroEntityType.registerMahiroEntity();
		MahiroWorldGen.initialization();
	}
}