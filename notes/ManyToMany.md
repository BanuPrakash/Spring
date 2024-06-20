
Many-To-Many:
```
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="movie_id")
    private int movieId;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="movie_actors",
            joinColumns= @JoinColumn(name="mid"),
            inverseJoinColumns= @JoinColumn(name="aid")
    )
    private List<Actor> actors = new ArrayList<>();

}
@Entity
@Table(name="actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="actor_id")
    private int actorId;

    private String name;
}

@Component
@AllArgsConstructor
public class MovieClient implements CommandLineRunner {
    private final MovieService movieService;
    @Override
    public void run(String... args) throws Exception {
       // first();
       // second();
        third();
    }

    private void third() {
        movieService.assignActorToMovie(2, 1);
    }

    private void second() {
        Movie m = new Movie();
        m.setName("Broken Arrow");
        movieService.addMovieWithActors(m);
    }

    private void first() {
        Movie m1 = new Movie();
        m1.setName("Pulp Fiction");

        Actor a1 = new Actor();
        a1.setName("John Travolta");

        Actor a2 = new Actor();
        a2.setName("Uma Thruman");

        m1.getActors().add(a1);
        m1.getActors().add(a2);
        movieService.addMovieWithActors(m1);
    }
}

```