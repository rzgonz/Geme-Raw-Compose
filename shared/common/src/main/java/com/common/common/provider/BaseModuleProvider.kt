package com.common.common.provider

import org.koin.core.module.Module

interface BaseModuleProvider {

    val modules: List<Module>

}