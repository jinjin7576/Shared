-- 복사용 쿼리
-- 테이블명과 컬럼명들을 들을 수정하여 사용
INSERT INTO tbl_board (bno, title, writer, content)
SELECT seq_board.NEXTVAL, title, writer, content
FROM tbl_board;