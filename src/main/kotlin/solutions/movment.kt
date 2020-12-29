package solutions

data class Point3D(val x: Int, val y: Int, val z: Int) : Point {
    override val neighbors: List<Point3D> by lazy {
        (x - 1..x + 1).flatMap { dx ->
            (y - 1..y + 1).flatMap { dy ->
                (z - 1..z + 1).mapNotNull { dz ->
                    Point3D(dx, dy, dz).takeUnless { it == this }
                }
            }
        }
    }

    operator fun plus(other: Point3D): Point3D =
            Point3D(x + other.x, y + other.y, z + other.z)

    fun hexNeighbor(dir: String): Point3D =
            if (dir in HEX_OFFSETS) HEX_OFFSETS.getValue(dir) + this
            else throw IllegalArgumentException("No dir: $dir")

    val hexNeighbors: List<Point3D> by lazy {
        HEX_OFFSETS.map { this + it.value }
    }

    companion object {
        val ORIGIN = Point3D(0, 0, 0)
        val HEX_OFFSETS = mapOf(
                "e" to Point3D(1, -1, 0),
                "w" to Point3D(-1, 1, 0),
                "ne" to Point3D(1, 0, -1),
                "nw" to Point3D(0, 1, -1),
                "se" to Point3D(0, -1, 1),
                "sw" to Point3D(-1, 0, 1),
        )
    }
}

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) : Point {

    override val neighbors: List<Point4D> by lazy {
        (x - 1..x + 1).flatMap { dx ->
            (y - 1..y + 1).flatMap { dy ->
                (z - 1..z + 1).flatMap { dz ->
                    (w - 1..w + 1).mapNotNull { dw ->
                        Point4D(dx, dy, dz, dw).takeUnless { it == this }
                    }
                }
            }
        }
    }
}