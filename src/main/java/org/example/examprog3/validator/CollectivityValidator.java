package org.example.examprog3.validator;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.example.examprog3.entity.Member;
import org.example.examprog3.entity.dto.CreateCollectivity;
import org.example.examprog3.entity.dto.CreateStructure;
import org.example.examprog3.exception.NotFoundException;
import org.example.examprog3.repository.MemberRepository;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CollectivityValidator {
    private final MemberRepository memberRepository;

    public void validateCollectivityCreation(CreateCollectivity createCollectivity) throws BadRequestException {

        if(!createCollectivity.isFederationApproval()){
            throw new BadRequestException("Collectivity must have federation approval");
        }

        if(createCollectivity.getLocation() == null){
            throw new BadRequestException("Collectivity must have location");
        }


        List<String> memberIds = createCollectivity.getMemberIds();
        if (memberIds == null || memberIds.isEmpty()) {
            throw new BadRequestException("Collectivity must have members");
        }

        validateAllMembersExist(memberIds);

        List<Member> members = memberRepository.findByIds(memberIds);

        if (members.size() < 10) {
            throw new BadRequestException(
                    String.format("Collectivity must have at least 10 members (currently has %d)", members.size())
            );
        }

        long membersWithSeniority = members.stream()
                .filter(Member::isAValidSponsor)
                .count();

        if (membersWithSeniority < 5) {
            throw new BadRequestException(
                    String.format("Collectivity must have at least 5 members with 6+ months seniority (currently has %d)",
                            membersWithSeniority)
            );
        }

        validateStructure(createCollectivity.getStructure(), memberIds);
    }


    private void validateAllMembersExist(List<String> memberIds) {
        List<Integer> missingIds = new ArrayList<>();

        for (String id : memberIds) {
            if (!memberRepository.existsById(Integer.valueOf(id))) {
                missingIds.add(Integer.valueOf(id));
            }
        }

        if (!missingIds.isEmpty()) {
            throw new NotFoundException("Members not found with IDs: " + missingIds);
        }
    }



    private void validateStructure(CreateStructure structure, List<String> memberIds) throws BadRequestException {
        if (structure == null) {
            throw new BadRequestException("Collectivity structure is required");
        }

        if (structure.getPresidentId() == null) {
            throw new BadRequestException("President ID is required");
        }
        if (structure.getVicePresidentId() == null) {
            throw new BadRequestException("Vice President ID is required");
        }
        if (structure.getTreasurerId() == null) {
            throw new BadRequestException("Treasurer ID is required");
        }
        if (structure.getSecretaryId() == null) {
            throw new BadRequestException("Secretary ID is required");
        }

        validateStructureMemberExists(Integer.valueOf(structure.getPresidentId()), "President");
        validateStructureMemberExists(Integer.valueOf(structure.getVicePresidentId()), "Vice President");
        validateStructureMemberExists(Integer.valueOf(structure.getTreasurerId()), "Treasurer");
        validateStructureMemberExists(Integer.valueOf(structure.getSecretaryId()), "Secretary");

        validateStructureMemberInList(Integer.valueOf(structure.getPresidentId()), memberIds, "President");
        validateStructureMemberInList(Integer.valueOf(structure.getVicePresidentId()), memberIds, "Vice President");
        validateStructureMemberInList(Integer.valueOf(structure.getTreasurerId()), memberIds, "Treasurer");
        validateStructureMemberInList(Integer.valueOf(structure.getSecretaryId()), memberIds, "Secretary");

        validateNoDuplicateRoles(structure);
    }

    private void validateStructureMemberExists(Integer memberId, String role) {
        if (memberRepository.existsById(memberId)) {
            throw new NotFoundException(role + " not found with ID: " + memberId);
        }
    }

    private void validateStructureMemberInList(Integer memberId, List<String> memberIds, String role) throws BadRequestException {
        if (!memberIds.contains(memberId)) {
            throw new BadRequestException(role + " must be one of the collectivity members");
        }
    }

    private void validateNoDuplicateRoles(CreateStructure structure) throws BadRequestException {
        List<String> roleIds = List.of(
                structure.getPresidentId(),
                structure.getVicePresidentId(),
                structure.getTreasurerId(),
                structure.getSecretaryId()
        );

        long distinctCount = roleIds.stream().distinct().count();
        if (distinctCount != 4) {
            throw new BadRequestException("The same member cannot hold multiple specific posts (President, Vice President, Treasurer, Secretary)");
        }
    }

}
