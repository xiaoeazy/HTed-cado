
package com.huan.HTed.system.mapper;

import com.huan.HTed.system.dto.DocSequence;

public interface DocSequenceMapper {

    DocSequence lockDocSequence(DocSequence docSequence);

    int insert(DocSequence docSequence);

    int update(DocSequence docSequence);
}