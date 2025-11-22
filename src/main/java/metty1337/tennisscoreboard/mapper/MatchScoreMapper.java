package metty1337.tennisscoreboard.mapper;

import metty1337.tennisscoreboard.dto.MatchScoreDto;
import metty1337.tennisscoreboard.model.MatchScoreModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchScoreMapper {
    MatchScoreMapper INSTANCE = Mappers.getMapper(MatchScoreMapper.class);

    @Mapping(source = "playerOne.name", target = "playerOneName")
    @Mapping(source = "playerTwo.name", target = "playerTwoName")
    @Mapping(source = "score", target = "score")
    MatchScoreDto toDto(MatchScoreModel model);
}
