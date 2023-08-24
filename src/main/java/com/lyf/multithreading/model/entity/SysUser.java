package com.lyf.multithreading.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @describe
 * @Author joyous
 * @Date 2023/5/23
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = false)
public class SysUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer age;

    private Integer sex;
}
