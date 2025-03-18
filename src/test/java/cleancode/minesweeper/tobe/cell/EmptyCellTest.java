package cleancode.minesweeper.tobe.cell;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptyCellTest {

    @Test
    @DisplayName("Empty Cell 생성 후 LandMine 검사 false 리턴")
    void emptyCellIsNotLandMine() {
        // given
        EmptyCell emptyCell = new EmptyCell();
        // when
        boolean isLandMine = emptyCell.isLandMine();
        // then
        assertThat(isLandMine).isFalse();
    }

}
