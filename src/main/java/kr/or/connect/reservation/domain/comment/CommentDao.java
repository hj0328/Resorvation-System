package kr.or.connect.reservation.domain.comment;

import kr.or.connect.reservation.domain.comment.dto.Comment;
import kr.or.connect.reservation.domain.comment.dto.CommentImage;
import kr.or.connect.reservation.domain.comment.dto.CommentRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static kr.or.connect.reservation.domain.comment.CommentDaoSql.*;
import static kr.or.connect.reservation.utils.UtilConstant.*;

@Repository
public class CommentDao {
	private NamedParameterJdbcTemplate jdbc;

	private RowMapper<Comment> commentRowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
	private RowMapper<CommentImage> commentImageRowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);

	public CommentDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Comment> selectComments(int displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(DISPLAY_INFO_ID, displayInfoId);

		return jdbc.query(SELECT_COMMENT_BY_DISPLAY_ID, params, commentRowMapper);
	}

	public Optional<CommentImage> findCommentImageById(int commentId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(COMMENT_ID, commentId);
		CommentImage image = jdbc.queryForObject(SELECT_COMMENT_IMAGE_BY_COMMENT_ID, params, commentImageRowMapper);
		return Optional.of(image);
	}

	public Optional<Comment> findCommentById(int commentId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(COMMENT_ID, commentId);
		Comment comment = jdbc.queryForObject(SELECT_COMMENT_BY_ID, params, commentRowMapper);
		return Optional.of(comment);
	}

	public Optional<Integer> saveComment(Integer productId, Integer reservationInfoId, CommentRequest commentRequest) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue(PRODUCT_ID, productId)
				.addValue(RESERVATION_INFO_ID, reservationInfoId)
				.addValue(COMMENT, commentRequest.getComment())
				.addValue(SCORE, commentRequest.getScore());

		jdbc.update(INSERT_COMMENT, params, keyHolder);
		int pk = keyHolder.getKey().intValue();
		return Optional.of(pk);
	}

	public Optional<Integer> countByReservationInfoId(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(RESERVATION_INFO_ID, reservationInfoId);

		Integer count = jdbc.queryForObject(SELECT_COUNT_ALL_BY_RESERVATION_INFO_ID, params, Integer.class);
		return Optional.of(count);
	}

	public void updateComment(Integer commentId, CommentRequest commentRequest) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue(COMMENT_ID, commentId)
				.addValue(COMMENT, commentRequest.getComment())
				.addValue(SCORE, commentRequest.getScore());

		jdbc.update(UPDATE_COMMENT_AND_SCORE, params);
	}

	public void deleteCommentById(Integer commentId) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue(COMMENT_ID, commentId);

		jdbc.update(DELETE_BY_ID, params);
	}
}
