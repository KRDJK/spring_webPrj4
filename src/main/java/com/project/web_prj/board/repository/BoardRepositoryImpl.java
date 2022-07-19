package com.project.web_prj.board.repository;

import com.project.web_prj.board.domain.Board;
import com.project.web_prj.common.paging.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Log4j2
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final JdbcTemplate template;


    // lombok을 안쓴다면 이렇게 해줘야 함!! 이게 원문이다.
//    @Autowired
//    public BoardRepositoryImpl(JdbcTemplate template) {
//        this.template = template;
//    }


    @Override
    public boolean save(Board board) {
        log.info("save process with jdbc - {}", board.toString());

        String sql = "INSERT INTO tbl_board (board_no, writer, title, content) VALUES (seq_tbl_board.nextval, ?, ?, ?)";

        return template.update(sql, board.getWriter(), board.getTitle(), board.getContent()) == 1;
    }


    @Override
    public List<Board> findAll() {
//        String sql = "SELECT * FROM tbl_board ORDER BY board_no DESC";

        String sql = "SELECT  *\n" +
                    "FROM (SELECT ROWNUM rn, v_board.*\n" +
                    "        FROM (\n" +
                    "                SELECT *\n" +
                    "                FROM tbl_board\n" +
                    "                ORDER BY board_no DESC\n" +
                    "                ) v_board)\n" +
                    "WHERE rn BETWEEN 1 AND 10";

        return template.query(sql, (rs, rowNum) -> new Board(rs));
    }

    @Override
    public List<Board> findAll(Page page) {

        /*
            만약에 1페이지를 보고 싶고 10개씩 보고 싶으면
            -> BETWEEN 1 AND 10
            만약에 2페이지를 보고 싶고 10개씩 보고 싶으면
            -> BETWEEN 11 AND 20

            만약에 1페이지를 보고 싶고 20개씩 보고 싶으면
            -> BETWEEN 1 AND 20
            만약에 2페이지를 보고 싶고 20개씩 보고 싶으면
            -> BETWEEN 21 AND 40

            공식 도출 : BETWEEN [ (pageNum-1) * amount + 1 ]
                                AND [ pageNum * amount ]
        */


        String sql = "SELECT  *\n" +
                "FROM (SELECT ROWNUM rn, v_board.*\n" +
                "        FROM (\n" +
                "                SELECT *\n" +
                "                FROM tbl_board\n" +
                "                ORDER BY board_no DESC\n" +
                "                ) v_board)\n" +
                "WHERE rn BETWEEN ? AND ?";

        return template.query(sql, (rs, rowNum) -> new Board(rs),
                (page.getPageNum()-1) * page.getAmount() + 1,
                        page.getPageNum() * page.getAmount());
    }


    @Override
        // queryForObject의 리턴 타입은 제네릭 타입이기 때문에 이 메서드에서 Board 타입을 리턴하기로 해서 Board로 잡아주는 것.
    public Board findOne(Long boardNo) {
        String sql = "SELECT * FROM tbl_board WHERE board_no=?";

                                        // rowNum은 안쓰지만 오버라이딩 규약에 따라 가는 것일 뿐!
        return template.queryForObject(sql, (rs, rowNum) -> new Board(rs), boardNo);
    }


    @Override
    public boolean remove(Long boardNo) {
        String sql = "DELETE FROM tbl_board WHERE board_no=?";

        return template.update(sql, boardNo) == 1;
    }


    @Override
    public boolean modify(Board board) {
        String sql = "UPDATE tbl_board SET writer = ?, title = ?, content = ? WHERE board_no = ?";

        return template.update(sql, board.getWriter(), board.getTitle(), board.getContent(), board.getBoardNo()) == 1;
    }


    @Override
    public Long getTotalCount() {
        String sql = "SELECT COUNT(board_no) AS cnt FROM tbl_board";

//        return template.queryForObject(sql, (rs, rowNum) -> rs.getLong("cnt"));
        return template.queryForObject(sql, Long.class); // 음..?
    }

    @Override
    public void upViewCount(Long boardNo) {
        String sql = "UPDATE tbl_board SET view_cnt = view_cnt+1 WHERE board_no = ?";

        template.update(sql, boardNo);
    }
}
