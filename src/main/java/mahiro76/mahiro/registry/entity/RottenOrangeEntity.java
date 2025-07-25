package mahiro76.mahiro.registry.entity;

import mahiro76.mahiro.registry.MahiroEntityType;
import mahiro76.mahiro.registry.MahiroItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

/**
 * 表示游戏世界中的可投掷腐烂橙子实体。
 * <p>
 * 该实体行为类似于雪球，但对 {@link BlazeEntity}（烈焰人）有额外伤害效果。
 * 当与实体或方块碰撞时，会生成物品粒子特效并从世界中移除自身。
 * </p>
 */
public class RottenOrangeEntity extends ThrownItemEntity {
    /**
     * 使用指定的实体类型和世界构造腐烂橙子实体。
     *
     * @param entityType 实体类型
     * @param world      实体所在的世界
     */
    public RottenOrangeEntity(EntityType<? extends RottenOrangeEntity> entityType, World world) {
        super(entityType, world);
    }
    /**
     * 使用指定的世界和拥有者构造腐烂橙子实体。
     *
     * @param world 实体所在的世界
     * @param owner 抛掷该实体的生物实体
     */
    public RottenOrangeEntity(World world, LivingEntity owner) {
        super(MahiroEntityType.RottenOrange, owner, world);
    }
    /**
     * 在指定坐标处于指定世界中构造腐烂橙子实体。
     *
     * @param world 实体所在的世界
     * @param x     x 坐标
     * @param y     y 坐标
     * @param z     z 坐标
     */
    public RottenOrangeEntity(World world, double x, double y, double z) {
        super(MahiroEntityType.RottenOrange, x, y, z, world);
    }

    @Override
    protected boolean canHit(Entity entity) {
        return super.canHit(entity) && entity != this.getOwner();
    }
    /**
     * 获取该实体销毁时的粒子特效。
     * 如果物品栈为空，则使用雪球粒子，否则使用物品粒子。
     *
     * @return 粒子特效
     */
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }
    /**
     * 处理实体状态更新，例如实体销毁时生成粒子特效。
     *
     * @param status 状态字节
     */
    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for (int i = 0; i < 8; i++) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }
    /**
     * 当该实体击中其他实体时调用。
     * @param entityHitResult 实体碰撞结果
     */
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof BlazeEntity ? 3 : 0;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), (float)i);
    }
    /**
     * 当该实体与任何对象（实体或方块）发生碰撞时调用。
     * 触发粒子特效并移除实体。
     *
     * @param hitResult 碰撞结果
     */
    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
    /**
     * 当该实体与方块发生碰撞时调用。
     * 触发粒子特效并杀死实体。
     *
     * @param state 碰撞到的方块状态
     */
    @Override
    protected void onBlockCollision(BlockState state) { // called on collision with a block
        super.onBlockCollision(state);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.kill();
        }

    }
    /**
     * 获取该实体默认关联的物品（腐烂橙子）。
     *
     * @return 腐烂橙子物品
     */
    @Override
    protected Item getDefaultItem() {
        return MahiroItems.ROTTEN_ORANGE;
    }
}
