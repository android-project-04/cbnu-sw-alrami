package cbnu.io.cbnuswalrami.test.helper.fixture.community;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.community.application.service.CommunityWriteService;
import cbnu.io.cbnuswalrami.business.web.community.presentation.request.RequestCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.test.helper.fixture.file.FileFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType.*;

@Component
public class CommunityFixture {

    @Autowired
    private CommunityWriteService communityWriteService;

    @Autowired
    private SignupFixture signupFixture;

    public List<ResponseCommunity> write15Community() {
        Member member = signupFixture.signupMember(
                "ADKLa1234",
                "Abcd1234@!",
                "한글닉네임"
        );

        MultipartFile file = FileFixture.createFile();

        List<RequestCommunity> communities = new ArrayList<>();
        IntStream.rangeClosed(1, 15)
                .forEach(x -> communities.add(new RequestCommunity("title" + x, "desc" + x)));

        List<ResponseCommunity> responseCommunities = new ArrayList<>();
        for (RequestCommunity community : communities) {
            responseCommunities.add(communityWriteService.writeCommunity(community, file, member, COMMUNITY));
        }
        return responseCommunities;
    }

    public List<ResponseCommunity> write15EmploymentCommunity() {
        Member member = signupFixture.signupMember(
                "ADKLa1234",
                "Abcd1234@!",
                "한글닉네임"
        );

        MultipartFile file = FileFixture.createFile();

        List<RequestCommunity> communities = new ArrayList<>();
        IntStream.rangeClosed(1, 15)
                .forEach(x -> communities.add(new RequestCommunity("title" + x, "desc" + x)));

        List<ResponseCommunity> responseCommunities = new ArrayList<>();
        for (RequestCommunity community : communities) {
            responseCommunities.add(communityWriteService.writeCommunity(community, file, member, EMPLOYMENT));
        }
        return responseCommunities;
    }

    public List<ResponseCommunity> write15Community(Member member) {
        MultipartFile file = FileFixture.createFile();

        List<RequestCommunity> communities = new ArrayList<>();
        IntStream.rangeClosed(1, 15)
                .forEach(x -> communities.add(new RequestCommunity("title" + x, "desc" + x)));

        List<ResponseCommunity> responseCommunities = new ArrayList<>();
        for (RequestCommunity community : communities) {
            responseCommunities.add(communityWriteService.writeCommunity(community, file, member, COMMUNITY));
        }
        return responseCommunities;
    }
}
