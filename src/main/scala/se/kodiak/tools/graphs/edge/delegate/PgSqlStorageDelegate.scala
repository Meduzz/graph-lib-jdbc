package se.kodiak.tools.graphs.edge.delegate

import io.getquill._
import se.kodiak.tools.graphs.edge.EdgeStorageDelegate
import se.kodiak.tools.graphs.model.{Edge, Node, Relation}

object PgSqlStorageDelegate {
	def apply(db:String, prefix:String):PgSqlStorageDelegate = new PgSqlStorageDelegate(db, prefix)
}

case class DbEdge(graph:String, start:String, relationId:String, relationType:String, end:String)

class PgSqlStorageDelegate(val db:String, val prefix:String) extends EdgeStorageDelegate {

	val ctx = new JdbcContext[PostgresDialect, CamelCase](prefix)

	import ctx._

	private val jdbc = quote {
		ctx.querySchema[DbEdge]("graph", _.start -> "startNode", _.end -> "endNode")
	}

	override def onAdd(edge:Edge) = {
		val added = run(insert(edge))
		added == 1
	}

	override def onDelete(edge:Edge) = {
		val deleted = run(delete(edge))
		deleted > 0
	}

	override def initialize() = {
		val all = run(loadAll)
		all.map(edge2Edge)
	}

	private val loadAll = quote {
		jdbc.filter(_.graph == lift(db))
	}

	private def insert(edge:Edge) = quote {
		jdbc.insert(lift(DbEdge(db, edge.start.id, edge.relation.id, edge.relation.relType, edge.end.id)))
	}

	private def delete(edge:Edge) = quote {
		jdbc.filter(e => e.graph == lift(db) && e.start == lift(edge.start.id) && e.relationId == lift(edge.relation.id) && e.relationType == lift(edge.relation.relType) && e.end == lift(edge.end.id)).delete
	}

	private def edge2Edge(edge:DbEdge):Edge = Edge(Node(edge.start), Relation(edge.relationId, edge.relationType), Node(edge.end))

}
