package org.khu.system.mirror.domain.mapper;

import org.khu.system.mirror.domain.dto.MirrorResponseDto;
import org.khu.system.mirror.domain.dto.MirrorResultDto;
import org.khu.system.mirror.domain.model.Mirror;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MirrorMapper {

    Mirror toModel(MirrorResponseDto dto);

    MirrorResultDto toResultDto(Mirror mirror);
}