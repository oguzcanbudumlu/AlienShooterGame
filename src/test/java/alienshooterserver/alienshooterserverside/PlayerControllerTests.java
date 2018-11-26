package alienshooterserver.alienshooterserverside;

import alienshooterserver.alienshooterserverside.player.Player;
import alienshooterserver.alienshooterserverside.player.PlayerService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration
public class PlayerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerService playerService;

    Player player = new Player(0, "omer", "cetin");

/*
    @Test
    public void retrieveDetailsForCourse() throws Exception {

        Mockito.when(
                studentService.retrieveCourse(Mockito.anyString(),
                        Mockito.anyString())).thenReturn(mockCourse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/students/Student1/courses/Course1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{id:Course1,name:Spring,description:10 Steps}";

        // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
    */
/*
    @Test
    public void getPlayerTest() throws Exception {
        playerService.registerPlayer(player);
        Mockito.when(playerService.getPlayer(0)).thenReturn(player);
        System.out.println("here");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/players/0").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse());
    }

*/
}
