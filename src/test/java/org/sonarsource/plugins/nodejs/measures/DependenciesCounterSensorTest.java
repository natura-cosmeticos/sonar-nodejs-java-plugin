package org.sonarsource.plugins.nodejs.measures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamPackageOKJson;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamPackageNotOKJson;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamDependenciesAnalysisOKJson;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamDependenciesAnalysisNotOKJson;
import static org.sonarsource.plugins.nodejs.TestData.PACKAGE_OK_JSON_DEPENDENCIES;
import static org.sonarsource.plugins.nodejs.TestData.PACKAGE_OK_JSON_DEV_DEPENDENCIES;
import static org.sonarsource.plugins.nodejs.TestData.NUMBER_OF_DEPENDENCIES_OF_THE_DEPENDENCIES_ANALYSIS_FILE;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.measure.Metric;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.measure.NewMeasure;
import org.sonarsource.plugins.nodejs.AnswerDecorator;;

@RunWith(MockitoJUnitRunner.class)
public class DependenciesCounterSensorTest {

    @InjectMocks
    private DependenciesCounterSensor dependenciesCounterSensor;

    @Mock
    private FileSystem fileSystem;

    @Mock
    private SensorContext context;

    @Mock
    private InputFile inputFile;

    @Mock
    private NewMeasure<Integer> intMeasure;

    @Mock
    private FilePredicates filePredicates;

    @Mock
    private FilePredicate filePredicate;

    @Captor
    private ArgumentCaptor<Metric<Integer>> intMetricArgCaptor;

    @Captor
    private ArgumentCaptor<Integer> intArgCaptor;

    @Before
    public void setup() {
        when(context.fileSystem()).thenReturn(fileSystem);
        when(fileSystem.predicates()).thenReturn(filePredicates);
        when(filePredicates.hasRelativePath(anyString())).thenReturn(filePredicate);
        //when(fileSystem.inputFile(any(FilePredicate.class))).thenAnswer(new AnswerDecorator<>().add(null).add(inputFile));
        when(fileSystem.inputFile(any(FilePredicate.class))).thenReturn(inputFile);
        when(intMeasure.forMetric(any())).thenReturn(intMeasure);
        when(intMeasure.on(any(InputComponent.class))).thenReturn(intMeasure);
        when(intMeasure.withValue(anyInt())).thenReturn(intMeasure);
    }

    @Test
    public void shouldSetMetricsWhenDependenciesAnalysisBuilt() throws IOException {        
        when(inputFile.inputStream()).thenReturn(fileInputStreamDependenciesAnalysisOKJson());
        when(context.<Integer>newMeasure()).thenReturn(intMeasure);
        dependenciesCounterSensor.execute(context);
        verify(intMeasure, times(1)).forMetric(intMetricArgCaptor.capture());
        assertThat(intMetricArgCaptor.getAllValues().get(0)).isEqualTo(NodeJSMetrics.NUMBER_OF_DEPENDENCIES);
        verify(intMeasure, times(1)).withValue(intArgCaptor.capture());
        assertThat(intArgCaptor.getAllValues().get(0)).isEqualTo(NUMBER_OF_DEPENDENCIES_OF_THE_DEPENDENCIES_ANALYSIS_FILE);
    }

    @Test
    public void shouldNotSetMetricWhenDependenciesAnalysisFactoryThrownEntityBuildingException() throws IOException {
        when(inputFile.inputStream()).thenReturn(fileInputStreamDependenciesAnalysisNotOKJson());
        dependenciesCounterSensor.execute(context);
        verify(context, never()).newMeasure();        
    }

    @Test
    public void shouldNotSetMetricWhenInputFileIsNul() throws IOException {
        when(fileSystem.inputFile(any(FilePredicate.class))).thenReturn(null);
        dependenciesCounterSensor.execute(context);
        verify(context, never()).newMeasure();        
    }

    @Test
    public void shouldNotSetMetricWhenInputFileThrowsIOException() throws IOException {
        doThrow(IOException.class).when(inputFile).inputStream();
        dependenciesCounterSensor.execute(context);
        verify(context, never()).newMeasure();
    }

    /* //package.json metric disabled
    @Test
    public void shouldSetMetricsWhenNodeJSPackageBuilt() throws IOException {        
        when(inputFile.inputStream()).thenReturn(fileInputStreamPackageOKJson());
        when(context.<Integer>newMeasure()).thenReturn(intMeasure);
        dependenciesCounterSensor.execute(context);
        verify(intMeasure, times(2)).forMetric(intMetricArgCaptor.capture());
        assertThat(intMetricArgCaptor.getAllValues().get(0)).isEqualTo(NodeJSMetrics.NUMBER_OF_DEPENDENCIES);
        assertThat(intMetricArgCaptor.getAllValues().get(1)).isEqualTo(NodeJSMetrics.NUMBER_OF_DEV_DEPENDENCIES);
        verify(intMeasure, times(2)).withValue(intArgCaptor.capture());
        assertThat(intArgCaptor.getAllValues().get(0)).isEqualTo(PACKAGE_OK_JSON_DEPENDENCIES.size());
        assertThat(intArgCaptor.getAllValues().get(1)).isEqualTo(PACKAGE_OK_JSON_DEV_DEPENDENCIES.size());
    }

    @Test
    public void shouldNotSetMetricWhenNodeJSFactoryThrownEntityBuildingException() throws IOException {
        when(inputFile.inputStream()).thenReturn(fileInputStreamPackageNotOKJson());
        dependenciesCounterSensor.execute(context);
        verify(context, never()).newMeasure();        
    }   
    */
}