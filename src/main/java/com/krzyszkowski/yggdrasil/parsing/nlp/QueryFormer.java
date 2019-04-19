package com.krzyszkowski.yggdrasil.parsing.nlp;

import com.google.protobuf.Value;

import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
interface QueryFormer extends Function<Map<String, Value>, String>{
}
