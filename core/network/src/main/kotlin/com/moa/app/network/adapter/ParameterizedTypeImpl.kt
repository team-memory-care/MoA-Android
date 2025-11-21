package com.moa.app.network.adapter

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ParameterizedTypeImpl(
    private val raw: Class<*>,
    private val args: Type
) : ParameterizedType {
    override fun getRawType(): Type = raw
    override fun getActualTypeArguments(): Array<Type> = arrayOf(args)
    override fun getOwnerType(): Type? = null
}
