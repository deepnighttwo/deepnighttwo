package com.snda.mzang.tvtogether.server.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.snda.mzang.tvtogether.server.util.SC;

public class BaseDao {

	protected SqlSession getSqlSession() {
		SqlSession session = SC.sqlFactory.openSession(false);
		SqlSessionWrapper sessionWrapper = new SqlSessionWrapper(session);
		return sessionWrapper;
	}

	private static class SqlSessionWrapper implements SqlSession {

		SqlSession worker;

		private void finish() {
			worker.commit();
			worker.close();
		}

		public SqlSessionWrapper(SqlSession worker) {
			this.worker = worker;
		}

		@Override
		public Object selectOne(String statement) {
			try {
				return worker.selectOne(statement);
			} finally {
				finish();
			}
		}

		@Override
		public Object selectOne(String statement, Object parameter) {
			try {
				return worker.selectOne(statement, parameter);
			} finally {
				finish();
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public List selectList(String statement) {
			try {
				return worker.selectList(statement);
			} finally {
				finish();
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public List selectList(String statement, Object parameter) {
			try {

				return worker.selectList(statement, parameter);
			} finally {
				finish();
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public List selectList(String statement, Object parameter, RowBounds rowBounds) {
			try {

				return worker.selectList(statement, parameter, rowBounds);
			} finally {
				finish();
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Map selectMap(String statement, String mapKey) {
			try {

				return worker.selectMap(statement, mapKey);
			} finally {
				finish();
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Map selectMap(String statement, Object parameter, String mapKey) {
			try {

				return worker.selectMap(statement, parameter, mapKey);
			} finally {
				finish();
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		public Map selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
			try {

				return worker.selectMap(statement, parameter, mapKey, rowBounds);
			} finally {
				finish();
			}
		}

		@Override
		public void select(String statement, Object parameter, ResultHandler handler) {
			try {

				worker.select(statement, parameter, handler);
			} finally {
				finish();
			}
		}

		@Override
		public void select(String statement, ResultHandler handler) {
			try {

				worker.select(statement, handler);
			} finally {
				finish();
			}
		}

		@Override
		public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
			try {

				worker.select(statement, parameter, rowBounds, handler);
			} finally {
				finish();
			}
		}

		@Override
		public int insert(String statement) {
			try {

				return worker.insert(statement);
			} finally {
				finish();
			}
		}

		@Override
		public int insert(String statement, Object parameter) {
			try {

				return worker.insert(statement, parameter);
			} finally {
				finish();
			}
		}

		@Override
		public int update(String statement) {
			try {

				return worker.update(statement);
			} finally {
				finish();
			}
		}

		@Override
		public int update(String statement, Object parameter) {
			try {

				return worker.update(statement, parameter);
			} finally {
				finish();
			}
		}

		@Override
		public int delete(String statement) {
			try {

				return worker.delete(statement);
			} finally {
				finish();
			}
		}

		@Override
		public int delete(String statement, Object parameter) {
			try {
				return worker.delete(statement, parameter);
			} finally {
				finish();
			}
		}

		@Override
		public void commit() {

			worker.commit();
		}

		@Override
		public void commit(boolean force) {

			worker.commit(force);
		}

		@Override
		public void rollback() {

			worker.rollback();
		}

		@Override
		public void rollback(boolean force) {

			worker.rollback(force);
		}

		@Override
		public List<BatchResult> flushStatements() {

			return worker.flushStatements();
		}

		@Override
		public void close() {

			worker.close();
		}

		@Override
		public void clearCache() {

			worker.clearCache();
		}

		@Override
		public Configuration getConfiguration() {

			return worker.getConfiguration();
		}

		@Override
		public <T> T getMapper(Class<T> type) {

			return worker.getMapper(type);
		}

		@Override
		public Connection getConnection() {
			return worker.getConnection();
		}

	}

}
