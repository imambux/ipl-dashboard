package com.imambux.ipldashboard.data;

import com.imambux.ipldashboard.model.Match;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        Match.MatchBuilder matchBuilder = Match.builder()
                .id(Long.parseLong(matchInput.getId()))
                .city(matchInput.getCity())
                .date(LocalDate.parse(matchInput.getDate()))
                .playerOfMatch(matchInput.getPlayer_of_match())
                .venue(matchInput.getVenue())
                .tossWinner(matchInput.getToss_winner())
                .tossDecision(matchInput.getToss_decision())
                .result(matchInput.getResult())
                .resultMargin(matchInput.getResult_margin())
                .umpire1(matchInput.getUmpire1())
                .umpire2(matchInput.getUmpire2());

        setTeams(matchInput, matchBuilder);

        return matchBuilder.build();
    }

    private void setTeams(MatchInput matchInput, Match.MatchBuilder matchBuilder) {
        String firstInningsTeam;
        String secondInningsTeam;

        if ("bat".equals(matchInput.getToss_decision())) {
            firstInningsTeam = matchInput.getToss_winner();
            secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        } else {
            secondInningsTeam = matchInput.getToss_winner();
            firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                    ? matchInput.getTeam2() : matchInput.getTeam1();
        }

        matchBuilder.team1(firstInningsTeam);
        matchBuilder.team2(secondInningsTeam);
    }

}
