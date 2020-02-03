import org.scalatest.{FlatSpec, Matchers}
import com.github.blemale.scaffeine.{Cache, LoadingCache, Scaffeine}

import scala.concurrent.duration._

class ScaffeineCacheSpec extends FlatSpec with Matchers {

    "Cache" should "be created from Scaffeine builder" in {

        val cache: Cache[Int, String] =
            Scaffeine()
                .recordStats()
                .expireAfterWrite(1.hour)
                .maximumSize(500)
                .build[Int, String]()

        cache.put(1, "foo")

        cache.getIfPresent(1) should be(Some("foo"))
        cache.getIfPresent(2) should be(None)
    }

    "LoadingCache" should "be created from Scaffeine builder" in {

        val cache: LoadingCache[Int, String] =
            Scaffeine()
                .recordStats()
                .expireAfterWrite(1.hour)
                .maximumSize(500)
                .build((i: Int) => {
                    println(s"***********  foo$i  ********************")
                    s"foo$i"
                })

        cache.get(1) should be("foo1")
        cache.get(1) should be("foo1")
        cache.get(1) should be("foo1")
        cache.get(1) should be("foo1")
        cache.get(1) should be("foo1")
        cache.get(1) should be("foo1")
        cache.get(1) should be("foo1")
        cache.get(2) should be("foo2")
        cache.get(500) should be("foo500")
        cache.get(501) should be("foo501")
        cache.get(501) should be("foo501")
        cache.get(501) should be("foo501")
        cache.get(501) should be("foo501")
        cache.get(501) should be("foo501")
        cache.get(501) should be("foo501")
    }
}
