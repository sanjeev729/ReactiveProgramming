package com.ambitious.reactive;

import com.sun.tools.javac.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.function.Function;

public class MonoFlux {
    public Flux<String> getFlux() {

        return Flux.fromIterable(List.of("sanjeev", "kumar", "pal"));
    }

    public Flux<String> getFluxMap() {
        return Flux.fromIterable(List.of("sanjeev", "kumar", "pal")).map(String::toUpperCase);
    }

    public Flux<String> getFluxFlatMap() {

        return Flux.fromIterable(List.of("sanjeev", "kumar", "pal"))
                .flatMap(s -> Flux.just(s.split(""))).log();
    }

    public Flux<String> getFluxFlatMapAsync() {

        return Flux.fromIterable(List.of("sanjeev", "kumar", "pal"))
                .flatMap(s -> Flux.just(s.split(""))
                        .delayElements(Duration.ofMillis(new Random().nextInt(1000))))
                .log();
    }

    public Mono<String> getMono() {
        return Mono.just("sanjeev").log();
    }

    public Mono<List<String[]>> getMonoFlatMap() {
        return Mono.just("Sanjeev")
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log();
    }

    public Flux<String> getFluxFilter() {
        return Flux.fromIterable(List.of("sanjeev", "kumar", "pal"))
                .filter(s -> s.length() > 3)
                .log();
    }

    public Flux<String> getFluxFilterDoOn() {
        return Flux.fromIterable(List.of("sanjeev", "kumar", "pal"))
                .filter(s -> s.length() > 3).doOnNext(s -> System.out.println("data: " + s)).log();
    }

    public Flux<String> getFluxTransform() {
        Function<Flux<String>, Flux<String>> dataTrans = data -> data.filter(s -> s.length() > 3).map(String::toUpperCase);
        return Flux.fromIterable(List.of("sanjeev", "kumar", "pal"))
                .transform(dataTrans)
                .log();
    }

    public Flux<String> getFluxConcat() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("sanjeev", "kumar", "pal"));
        Flux<String> stringFlux1 = Flux.fromIterable(List.of("Sachin", "kumar", "pal"));

        return Flux.concat(stringFlux, stringFlux1).log();
    }

    public Flux<String> getFluxMerge() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("sanjeev", "kumar", "pal")).delayElements(Duration.ofSeconds(new Random().nextInt(3)));
        Flux<String> stringFlux1 = Flux.fromIterable(List.of("sachin", "kumar", "pal")).delayElements(Duration.ofSeconds(new Random().nextInt(4)));

        return Flux.merge(stringFlux, stringFlux1).log();
    }

    public Flux<String> getFluxZip() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("sanjeev", "kumar", "pal"));
        Flux<String> stringFlux1 = Flux.fromIterable(List.of("sachin", "kumar", "pal"));

        return Flux.zip(stringFlux, stringFlux1, (first, second) -> first + second).log();
    }

    public Flux<String> getFluxZipTuple() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("sanjeev", "kumar", "pal"));
        Flux<String> stringFlux1 = Flux.fromIterable(List.of("sachin", "kumar", "pal"));
        Flux<String> moreStrings = Flux.fromIterable(List.of("pratiksha", "kewala", "devi"));
        return Flux.zip(stringFlux, stringFlux1, moreStrings).map(s -> s.getT1() + s.getT2() + s.getT3())
                .log();
    }

    public Flux<String> getFluxOnErrorReturn() {
        return Flux.fromIterable(List.of("sanjeev", "kumar"))
                .concatWith(Flux.error(new RuntimeException("error occurred")))
                .onErrorReturn("Sachin");
    }

    public Flux<String> getFluxOnErrorContinue() {
        return Flux.fromIterable(List.of("sanjeev", "kumar"))
                .map(s -> {
                    if (s.equalsIgnoreCase("kumar")) {
                        throw new RuntimeException("Error occurred");
                    }
                    return s;
                })
                .onErrorContinue((t, s) -> {
                    System.out.println("t: " + t);
                    System.out.println("s: " + s);
                });
    }

    public Flux<String> getFluxOnErrorMap() {
        return Flux.fromIterable(List.of("sanjeev", "kumar"))
                .map(s -> {
                    if (s.equalsIgnoreCase("kumar")) {
                        throw new RuntimeException("Error occurred");
                    }
                    return s;
                })
                .onErrorMap((t) -> {
                    System.out.println("t: " + t);
                    return new IllegalStateException("error occurred");
                });
    }
}
