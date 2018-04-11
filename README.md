# tool
Java工具类

<update id="batchUpdate" parameterType="java.util.List">
    update chedai_product_cost
      <trim prefix="set" suffixOverrides=",">
        <trim prefix="cost_value =case" suffix="end,">
          <foreach collection="list" item="item" index="index">
            <if test="item.costValue!=null">
              WHEN id=#{item.id} THEN #{item.costValue}
            </if>
          </foreach>
        </trim>
        <trim prefix="version =case" suffix="end,">
          <foreach collection="list" item="item" index="index">
            <if test="item.version!=null">
              WHEN id=#{item.id} THEN #{item.version}+1
            </if>
          </foreach>
        </trim>
        <trim prefix="modify_time =case" suffix="end,">
          <foreach collection="list" item="item" index="index">
            <if test="item.modifyTime!=null">
              WHEN id=#{item.id} THEN #{item.modifyTime}
            </if>
          </foreach>
        </trim>
      </trim>
      WHERE
      <foreach collection="list" separator="or" item="item" index="index">
        id=#{item.id} AND version=#{item.version}
      </foreach>
  </update>