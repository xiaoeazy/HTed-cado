package com.huan.HTed.generator.service;

import com.huan.HTed.generator.service.impl.GeneratorInfo;

import java.util.List;


public interface IHapGeneratorService {
    public List<String> showTables();

    public int generatorFile(GeneratorInfo info);

}
