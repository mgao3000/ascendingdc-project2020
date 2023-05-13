package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.exception.ItemNotFoundException;
import com.ascendingdc.training.project2020.service.MajorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)

//@WebMvcTest(controllers = MajorController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MajorControllerTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("majorSpringDataJPADao")
    private MajorDao mockMajorDao;

    @MockBean
    private MajorService mockMajorService;

    @InjectMocks
    private MajorController majorController;

    @BeforeEach
    public void initEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMajors() throws Exception {
        List<MajorDto> spyMajorDtoList = spy(ArrayList.class);
        MajorDto majorDto = createMajorDtoByName("aaa");
        spyMajorDtoList.add(majorDto);

        when(mockMajorService.getMajors()).thenReturn(spyMajorDtoList);

        String uriForGetAllMajors = "/proj2020/majors";
        mockMvc.perform(get(uriForGetAllMajors)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

        verify(mockMajorService, times(1)).getMajors();
    }

    @Test
    public void testGetMajorById() throws Exception {
        MajorDto majorDto = createMajorDtoByName("aaa");
        Long majorId = 101L;;
        majorDto.setId(majorId);

        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorId);
        String responseJsonString = JsonStringUtil.convertObjectToJsonString(majorDto);

        when(mockMajorService.getMajorById(anyLong())).thenReturn(majorDto);

        String uriForGetMajorById = "/proj2020/majors/{id}";
        /*
         * version 1
         */
//        mockMvc.perform(get(uriForGetMajorById, majorId)
//                .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJsonString))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(responseJsonString));

        /*
         * version 2
         */
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uriForGetMajorById, majorId)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonString);

                mockMvc.perform(requestBuilder)
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().json(responseJsonString));

        verify(mockMajorService, times(1)).getMajorById(anyLong());
    }


    @Test
    public void testGetMajorById_Not_Found() throws Exception {
        MajorDto majorDto = createMajorDtoByName("aaa");
        Long majorId = 111L;;
        majorDto.setId(majorId);

        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorId);

        when(mockMajorService.getMajorById(anyLong())).thenThrow(new ItemNotFoundException("Could not find Major with is = " + majorId));
//        when(mockMajorDao.getMajorById(anyLong())).thenReturn(null);
//        Throwable thrown = assertThrows(EntityNotFoundException.class, () -> mockMajorService.getMajorById(majorId));
//        Throwable thrown = catchThrowable(() ->  mockMajorService.getMajorById(majorId));
//        assertThat(thrown).isExactlyInstanceOf(EntityNotFoundException.class);

        String uriForGetMajorById = "/proj2020/majors/{id}";
        /*
         * version 1
         */
//        mockMvc.perform(get(uriForGetMajorById, majorId)
//                .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJsonString))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", is("ItemNotFoundException")))
//                .andExpect(jsonPath("$.description", is("Could not find Major with is = "+majorId)));

        /*
         * version 2
         */
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uriForGetMajorById, majorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJsonString);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ItemNotFoundException")))
                .andExpect(jsonPath("$.description", is("Could not find Major with is = "+majorId)));

        verify(mockMajorService, times(1)).getMajorById(anyLong());
    }

    @Test
    public void testGetMajorByName() throws Exception {
        String majorName = "aaa";
        MajorDto majorDto = createMajorDtoByName(majorName);
        Long majorId = 101L;;
        majorDto.setId(majorId);

        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorName);
        String responseJsonString = JsonStringUtil.convertObjectToJsonString(majorDto);

        when(mockMajorService.getMajorByName(anyString())).thenReturn(majorDto);

        String uriForGetMajorByName = "/proj2020/majors/name/{name}";
        /*
         * version 1
         */
        mockMvc.perform(get(uriForGetMajorByName, majorName)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseJsonString));

        /*
         * version 2
         */
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get(uriForGetMajorByName, majorName)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJsonString);
//
//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(responseJsonString));

        verify(mockMajorService, times(1)).getMajorByName(anyString());
    }


    @Test
    public void testGetMajorByName_Not_Found() throws Exception {
        String majorName = "aaa";
        MajorDto majorDto = createMajorDtoByName(majorName);
        Long majorId = 111L;;
        majorDto.setId(majorId);

        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorName);

        when(mockMajorService.getMajorByName(anyString())).thenThrow(new ItemNotFoundException("Could not find Major with name = " + majorName));
//        when(mockMajorDao.getMajorById(anyLong())).thenReturn(null);
//        Throwable thrown = assertThrows(EntityNotFoundException.class, () -> mockMajorService.getMajorById(majorId));
//        Throwable thrown = catchThrowable(() ->  mockMajorService.getMajorById(majorId));
//        assertThat(thrown).isExactlyInstanceOf(EntityNotFoundException.class);

        String uriForGetMajorByName = "/proj2020/majors/name/{name}";
        /*
         * version 1
         */
        mockMvc.perform(get(uriForGetMajorByName, majorName)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJsonString))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("ItemNotFoundException")))
                .andExpect(jsonPath("$.description", is("Could not find Major with name = " + majorName)));

        /*
         * version 2
         */
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get(uriForGetMajorByName, majorName)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestJsonString);
//
//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", is("ItemNotFoundException")))
//                .andExpect(jsonPath("$.description", is("Could not find Major with name = "+majorName)));

        verify(mockMajorService, times(1)).getMajorByName(anyString());
    }

    @Test
    public void testCreateMajor() throws Exception {
        String uriForCreateAMajor = "/proj2020/majors";
//        MajorDto mockMajorDto = mock(MajorDto.class);

        String majorName = "bbb";
        MajorDto inputMajorDto = createMajorDtoByName(majorName);
        String requestJsonString = JsonStringUtil.convertObjectToJsonString(inputMajorDto);

        MajorDto savedMajorDto = createMajorDtoByName(majorName);
        savedMajorDto.setId(100L);
        String responseJsonString = JsonStringUtil.convertObjectToJsonString(savedMajorDto);

        when(mockMajorService.save(inputMajorDto)).thenReturn(savedMajorDto);

        mockMvc.perform(post(uriForCreateAMajor)
                .content(requestJsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJsonString))
                .andExpect(jsonPath("$.id", is(100)))
                .andExpect(jsonPath("$.name", comparesEqualTo(inputMajorDto.getName())))
                .andExpect(jsonPath("$.description", comparesEqualTo(inputMajorDto.getDescription())));

        verify(mockMajorService, times(1)).save(inputMajorDto);
    }
//    @Test    sample of using MockHttpServletRequest, need refactor to make it work
    @Test
    public void testUpdateMajor() throws Exception {
        String uriForUpdatingAMajor = "/proj2020/majors";

        String majorName = "bbb";
        Long majorId = 100L;
        MajorDto inputMajorDto = createMajorDtoByName(majorName);
        inputMajorDto.setId(majorId);
        String requestJsonString = JsonStringUtil.convertObjectToJsonString(inputMajorDto);

        MajorDto updateMajorDto = createMajorDtoByName(majorName);
        updateMajorDto.setId(majorId);
        updateMajorDto.setDescription(updateMajorDto.getDescription() + "_updated");
        String responseJsonString = JsonStringUtil.convertObjectToJsonString(updateMajorDto);

        when(mockMajorService.update(inputMajorDto)).thenReturn(updateMajorDto);

        mockMvc.perform(put(uriForUpdatingAMajor)
                    .content(requestJsonString)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(responseJsonString))
                    .andExpect(jsonPath("$.id", is(majorId.intValue())))
                    .andExpect(jsonPath("$.name", comparesEqualTo(updateMajorDto.getName())))
                    .andExpect(jsonPath("$.description", comparesEqualTo(updateMajorDto.getDescription())));

        verify(mockMajorService, times(1)).update(inputMajorDto);
    }

    @Test
    public void testDeleteMajorByMajorName_successful() throws Exception {
        when(mockMajorService.deleteByName(anyString())).thenReturn(true);

        String uriForDeleteMajorByName = "/proj2020/majors/name/{name}";

        String majorName = "aaa";
        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorName);

        mockMvc.perform(delete(uriForDeleteMajorByName, majorName)
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(mockMajorService, times(1)).deleteByName(anyString());
    }

    @Test
    public void testDeleteMajorByMajorName_failed() throws Exception {
        when(mockMajorService.deleteByName(anyString())).thenReturn(false);

        String uriForDeleteMajorByName = "/proj2020/majors/name/{name}";

        String majorName = "aaa";
        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorName);

        mockMvc.perform(delete(uriForDeleteMajorByName, majorName)
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(mockMajorService, times(1)).deleteByName(anyString());

    }

    @Test
    public void testDeleteMajorByMajorId_successful() throws Exception {
        when(mockMajorService.deleteById(anyLong())).thenReturn(true);

        String uriForDeleteMajorById = "/proj2020/majors/{id}";

        Long majorId = 300L;
        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorId);

        mockMvc.perform(delete(uriForDeleteMajorById, majorId)
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(mockMajorService, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteMajorByMajorId_failed() throws Exception {
        when(mockMajorService.deleteById(anyLong())).thenReturn(false);

        String uriForDeleteMajorById = "/proj2020/majors/{id}";

        Long majorId = 300L;
        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorId);

        mockMvc.perform(delete(uriForDeleteMajorById, majorId)
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(mockMajorService, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteMajorByMajorDto_successful() throws Exception {
        String majorName = "aaa";
        MajorDto majorDto = createMajorDtoByName(majorName);
        majorDto.setId(101L);
        when(mockMajorService.delete(majorDto)).thenReturn(true);

        String uriForDeleteMajor = "/proj2020/majors";

        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorDto);

        mockMvc.perform(delete(uriForDeleteMajor, majorDto)
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(mockMajorService, times(1)).delete(majorDto);
    }

    @Test
    public void testDeleteMajor_failed() throws Exception {
        String majorName = "aaa";
        MajorDto majorDto = createMajorDtoByName(majorName);
        majorDto.setId(101L);
        when(mockMajorService.delete(majorDto)).thenReturn(false);

        String uriForDeleteMajor = "/proj2020/majors";

        String requestJsonString = JsonStringUtil.convertObjectToJsonString(majorDto);

        mockMvc.perform(delete(uriForDeleteMajor, majorDto)
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verify(mockMajorService, times(1)).delete(majorDto);
    }




    //    @Test    sample of using MockHttpServletRequest, need refactor to make it work
    public void testCreateMajor_then_return_responseEntity_with_URI() throws Exception {
        String uriForCreateAMajor = "/proj2020/majors";
//        MajorDto mockMajorDto = mock(MajorDto.class);

        String majorName = "bbb";
        MajorDto inputMajorDto = createMajorDtoByName(majorName);
        String requestJsonString = JsonStringUtil.convertObjectToJsonString(inputMajorDto);

        MajorDto savedMajorDto = createMajorDtoByName(majorName);
        savedMajorDto.setId(100L);
 //       String responseJsonString = JsonStringUtil.convertObjectToJsonString(savedMajorDto);

        MockHttpServletRequest request = new MockHttpServletRequest();
       logger.info("=======, request.getPathInfo()={}", request.getPathInfo());
        logger.info("=======, request.getContextPath()={}", request.getContextPath());
        logger.info("=======, request.getServletPath()={}", request.getServletPath());
        logger.info("=======, request.getRequestURI()={}", request.getRequestURI());
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMajorDto.getId())
                .toUri();
        ResponseEntity<Object> response = ResponseEntity.created(location).build();
        String responseJsonString = JsonStringUtil.convertObjectToJsonString(response);
        String locationJsonString = JsonStringUtil.convertObjectToJsonString(location);

        logger.info("======= responseJsonString={}", responseJsonString);
        logger.info("=======,  locationJsonString={}", locationJsonString);

        when(mockMajorService.save(inputMajorDto)).thenReturn(savedMajorDto);

        String hardcodedString = "{\"headers\":{\"Location\":[\"http://localhost/proj2020/majors/100\"]},\"body\":null,\"statusCode\":\"CREATED\",\"statusCodeValue\":201}";
        MvcResult mvcResult = mockMvc.perform(post(uriForCreateAMajor)
                        .content(requestJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                        .andReturn();
//                .andExpect(content().json(locationJsonString));
///        .andExpect(jsonPath("$.statusCode", hasItem("CREATED")))
///                .andExpect(jsonPath("$.URL", hasItem("http://localhost/proj2020/majors/100")));

        String responseContent = mvcResult.getResponse().getContentAsString(); //.getResponse().getContentAsString();
        logger.info("=====, responseContent={}", responseContent);
        verify(mockMajorService, times(1)).save(inputMajorDto);
    }

    protected MajorDto createMajorDtoByName(String name) {
        MajorDto majorDto = new MajorDto();
        majorDto.setName(name);
        majorDto.setDescription(name + "--description");
        return majorDto;
    }

}
