package com.tg;

import java.nio.charset.Charset;

import com.github.mauricio.async.db.Configuration;
import com.github.mauricio.async.db.Connection;
import com.github.mauricio.async.db.QueryResult;
import com.github.mauricio.async.db.mysql.MySQLConnection;
import com.github.mauricio.async.db.mysql.util.CharsetMapper;
import com.github.mauricio.async.db.mysql.util.URLParser;
import com.tg.async.mysql.ScalaUtils;
import com.tg.async.mysql.VertxEventLoopExecutionContext;

import io.vertx.core.Vertx;
import scala.concurrent.Future;

public class AsyncMySQL {

	public static void main(String[] args) throws Exception {
		Vertx vertx = Vertx.vertx();
		Configuration configuration = URLParser.parse(
				"jdbc:mysql://10.100.216.147:3306/test?user=test&password=", Charset.forName("UTF-8"));

		Connection connection = new MySQLConnection(configuration, CharsetMapper.Instance(),
				vertx.nettyEventLoopGroup().next(), VertxEventLoopExecutionContext.create(vertx));


		Future<Connection> fc = connection.connect();
		fc.onComplete(ScalaUtils.toFunction1(c -> {
			Future<QueryResult> future = c.result().sendQuery("select * from T_User");
			future.onComplete(ScalaUtils.toFunction1(tr -> {
				if (tr.succeeded()) {
					System.out.println("su:" + tr);
					tr.map(qr -> {
						qr.rows().map(x -> {
							System.out.println(x);
							return null;
						});
						return null;
					});
				} else {
					System.out.println("fa:" + tr.cause());
				}
			}), VertxEventLoopExecutionContext.create(vertx));
		}), VertxEventLoopExecutionContext.create(vertx));


		Thread.sleep(1000);
		connection.disconnect();

	}

}
