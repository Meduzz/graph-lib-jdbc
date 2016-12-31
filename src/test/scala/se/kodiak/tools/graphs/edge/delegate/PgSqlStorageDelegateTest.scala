package se.kodiak.tools.graphs.edge.delegate

import org.scalatest.{BeforeAndAfterAll, FunSuite}
import se.kodiak.tools.graphs.Graph
import se.kodiak.tools.graphs.edge.EdgeStorage
import se.kodiak.tools.graphs.model._
import se.kodiak.tools.graphs.Implicits._

class PgSqlStorageDelegateTest extends FunSuite with BeforeAndAfterAll {
	val db = "jdbcTest"

	implicit val edges = EdgeStorage(PgSqlStorageDelegate(db))
	implicit val graph = Graph(edges)

	override protected def beforeAll() = {
		edges.edges.foreach(edges.remove)
	}

	test("edges are stored properly") {
		val result = addOne("1", "2", "1")

		val fresh = EdgeStorage(PgSqlStorageDelegate(db))
		assert(fresh.edges.nonEmpty, "At least one edge was expected.")
	}

	test("edges are removed correctly") {
		val edge1 = addOne("3", "4", "2")
		edge1.start.delete()

		Node("2").delete()

		assert(edges.edges.isEmpty)
	}

	def addOne(start:String, end:String, rel:String):Edge = {
		val first = Node(start)
		val second = Node(end)
		val relation = Relation(rel, "SPAM")
		edges.add(first, relation, second)
	}
}
