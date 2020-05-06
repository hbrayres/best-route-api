package br.com.selecao.bestroute.util;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

public class GenericConvert {

	private GenericConvert() {
		// do nothing
	}

	public static <S, D> void mapSkipNull(final S source, final D destination, final boolean skipNull) {

		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(skipNull).setMatchingStrategy(STRICT);
		modelMapper.map(source, destination);
	}

	public static <S, D> D map(final S source, final Class<D> destinationType) {

		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(STRICT);
		return modelMapper.map(source, destinationType);
	}

	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}

}
