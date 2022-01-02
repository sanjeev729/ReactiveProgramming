package com.ambitious.reactive;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import reactor.test.StepVerifier;

@Slf4j
public class MonoFluxTest extends TestCase {
    MonoFlux monoFlux = new MonoFlux();

    public void testGetFlux() {
        var getFlux = monoFlux.getFlux().log();
        getFlux.subscribe(s -> log.info("event: {}", s));
    }

    public void testGetFluxMap() {
        var getFlux = monoFlux.getFluxMap().log();
        StepVerifier.create(getFlux).expectNext("SANJEEV", "KUMAR", "PAL").verifyComplete();
    }

    public void testTestGetFluxFlatMap() {
        var getFlux = monoFlux.getFluxFlatMap();
        StepVerifier.create(getFlux).expectNextCount(15).verifyComplete();
    }

    public void testGetFluxFlatMapAsync() {
        var getFlux = monoFlux.getFluxFlatMapAsync();
        StepVerifier.create(getFlux).expectNextCount(15).verifyComplete();
    }

    public void testGetMono() {
        var getMono = monoFlux.getMono();
        StepVerifier.create(getMono).expectNextCount(1).verifyComplete();
    }

    public void testGetMonoFlatMap() {
        var getMono = monoFlux.getMonoFlatMap();
        StepVerifier.create(getMono).expectNextCount(1).verifyComplete();
    }

    public void testGetFluxFilter() {
        var getFlux = monoFlux.getFluxFilter();
        StepVerifier.create(getFlux).expectNext("sanjeev","kumar").verifyComplete();
    }

    public void testGetFluxTransform() {
        var getFlux = monoFlux.getFluxTransform();
        StepVerifier.create(getFlux).expectNext("SANJEEV","KUMAR").verifyComplete();
    }

    public void testGetFluxConcat() {
        var getFlux = monoFlux.getFluxConcat();
        StepVerifier.create(getFlux).expectNext("sanjeev", "kumar", "pal", "Sachin", "kumar", "pal").verifyComplete();
    }

    public void testGetFluxMerge() {
        var getFlux = monoFlux.getFluxMerge();
        StepVerifier.create(getFlux).expectNextCount(6).verifyComplete();
    }

    public void testGetFluxZip() {
        var getFlux = monoFlux.getFluxZip();
        StepVerifier.create(getFlux).expectNext("sanjeevsachin", "kumarkumar", "palpal").verifyComplete();
    }

    public void testGetFluxZipTuple() {
        var getFlux = monoFlux.getFluxZipTuple();
        StepVerifier.create(getFlux).expectNext("sanjeevsachinpratiksha", "kumarkumarkewala", "palpaldevi").verifyComplete();
    }

    public void testGetFluxFilterDoOn() {
        var getFlux = monoFlux.getFluxFilterDoOn();
        StepVerifier.create(getFlux).expectNext("sanjeev","kumar").verifyComplete();
    }

    public void testGetFluxException() {
        var getFlux = monoFlux.getFluxOnErrorReturn().log();
        StepVerifier.create(getFlux)
                .expectNext("sanjeev","kumar","Sachin").verifyComplete();
    }

    public void testGetFluxOnErrorContinue() {

        var getFlux = monoFlux.getFluxOnErrorContinue().log();
        StepVerifier.create(getFlux)
                .expectNext("sanjeev").verifyComplete();
    }

    public void testGetFluxOnErrorMap() {
        var getFlux = monoFlux.getFluxOnErrorMap().log();
        StepVerifier.create(getFlux)
                .expectNext("sanjeev")
                .expectError(IllegalStateException.class).verify();
    }
}