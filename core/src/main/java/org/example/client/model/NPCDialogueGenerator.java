package org.example.client.model;

import org.example.common.utils.OllamaService;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Player;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.Weathers.Weather;

public class NPCDialogueGenerator implements Runnable {
    private final static String NPCRole =
            "You are an NPC in Stardew Valley. **Strict Rules:**\n" +
                    "1. Speak 1-3 short, natural sentences **in character**.\n" +
                    "2. **Explicitly name** the current season (spring/summer/fall/winter) and weather (sunny/rainy/stormy/snowy).\n" +  // Generalized for all conditions
                    "3. **Tone scale**:\n" +
                    "   0=Neutral (stranger) | 1=Polite (acquaintance) | 2=Friendly\n" +
                    "   3=Warm (close friend) | 4=Affectionate (best friend)\n" +
                    "4. **NEVER** add:\n" +
                    "   - Explanations, notes, or OOC text\n" +
                    "   - Dialogue beyond 3 sentences.";
    private final static String[] friendshipTone = {"neutral", "polite", "friendly", "warm", "affectionate"};

    private final NPC npc;
    private final Player currentPlayer;

    public NPCDialogueGenerator(NPC npc, Player currentPlayer) {
        this.npc = npc;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void run() {
        npc.setThinking(true);
        Season season = ClientApp.getCurrentGame().getTime().getSeason();
        Weather weather = ClientApp.getCurrentGame().getCurrentWeather();
        int friendshipLevel = npc.getRelations().computeIfAbsent(currentPlayer, k->new Relation()).getLevel();
        try {
            String dialogue = OllamaService.queryModelWithRole("phi3", NPCRole,
                    "Context:\n" +
                            "- Season: " + season.name() + " (say '" + season.name() + "')\n" +
                            "- Weather: " + weather.name() + " (say '" + weather.name() + "')\n" +
                            "- Friendship: " + friendshipLevel + "/4 (" + friendshipTone[friendshipLevel] + " tone)\n\n" +
                            "Reply with 1-3 short sentences IN CHARACTER.\n" +
                            "NO EXTRA TEXT. JUST THE DIALOGUE\n" +
                            "Example:\n" +
                            getDialogueExample(season, weather, friendshipLevel));
            npc.setDialogue(dialogue);
            npc.setThinking(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDialogueExample(Season season, Weather weather, int friendshipLevel) {
        if (season == Season.Spring) {
            if (weather == Weather.Sunny) {
                switch (friendshipLevel) {
                    case 0: return "Sunny spring days make the flowers bloom. Don't touch my tulips.";
                    case 1: return "The spring sun is strong today. You might want a hat for farming.";
                    case 2: return "Beautiful sunny spring morning! Want to help me water the crops?";
                    case 3: return "Remember how we sunbathed by the river last spring? Good times.";
                    case 4: return "This sunny weather calls for a picnic! I packed your favorite salad.";
                }
            }
            else if (weather == Weather.Rainy) {
                switch (friendshipLevel) {
                    case 0: return "Rain in spring means more mushrooms. Not that I care what you do.";
                    case 1: return "Spring rain makes the soil perfect for planting potatoes.";
                    case 2: return "Love the sound of spring rain on my roof! Want to listen together?";
                    case 3: return "This rain reminds me of when we danced in puddles last year.";
                    case 4: return "Let's splash in these spring puddles like we did when we first met!";
                }
            }
            else if (weather == Weather.Stormy) {
                switch (friendshipLevel) {
                    case 0: return "Spring storms are loud. Go away.";
                    case 1: return "Stormy spring weather requires sturdy fences. Just a tip.";
                    case 2: return "I baked cookies for this spring storm! Want to share some?";
                    case 3: return "Remember how we sheltered the chickens during last spring's storm?";
                    case 4: return "Stormy nights are cozy! Let's cuddle by my fireplace like last spring.";
                }
            }
            else if (weather == Weather.Snowy) {
                switch (friendshipLevel) {
                    case 0: return "Snow in spring? Weird.";
                    case 1: return "Unusual spring snow. Better protect your crops.";
                    case 2: return "Spring snow melts fast! Let's make snow angels before it's gone!";
                    case 3: return "This takes me back to that crazy spring snow when we first met.";
                    case 4: return "Spring snow means hot cocoa! I made your favorite minty blend.";
                }
            }
        }
        else if (season == Season.Summer) {
            if (weather == Weather.Sunny) {
                switch (friendshipLevel) {
                    case 0: return "Too hot. Go away.";
                    case 1: return "The summer sun dries crops quickly. Water them twice.";
                    case 2: return "Perfect beach weather! Join me for a swim later?";
                    case 3: return "This heat reminds me of when we fainted at the Luau together!";
                    case 4: return "Let's skip work and spend the whole day at the beach, just us two!";
                }
            }
            else if (weather == Weather.Rainy) {
                switch (friendshipLevel) {
                    case 0: return "Rain cools summer heat. Finally.";
                    case 1: return "Summer rain means blueberries will be extra juicy this year.";
                    case 2: return "Rainy summer days are perfect for fishing! Need company?";
                    case 3: return "Remember how we got soaked picking melons last summer?";
                    case 4: return "Let's dance in the summer rain again! I brought towels this time.";
                }
            }
            else if (weather == Weather.Stormy) {
                switch (friendshipLevel) {
                    case 0: return "Summer lightning could strike you. Not my problem.";
                    case 1: return "Storm coming. Might want to check your lightning rods.";
                    case 2: return "Summer storms make great atmosphere for ghost stories! Interested?";
                    case 3: return "This takes me back to when we rode out the storm in my cellar.";
                    case 4: return "Scary storms are better together! Stay over at my place tonight?";
                }
            }
            else if (weather == Weather.Snowy) {
                return "Snow in summer? You must be dreaming."; // Impossible case
            }
        }
        else if (season == Season.Fall) {
            if (weather == Weather.Sunny) {
                switch (friendshipLevel) {
                    case 0: return "Sunny fall days mean harvest time. Get to work.";
                    case 1: return "The fall sun makes pumpkins grow bigger. Useful tip.";
                    case 2: return "Gorgeous fall colors today! Want to take a walk through the woods?";
                    case 3: return "This sunshine reminds me of when we won the Grange Display together.";
                    case 4: return "Perfect weather for our anniversary picnic! I found that spot you love.";
                }
            }
            else if (weather == Weather.Rainy) {
                switch (friendshipLevel) {
                    case 0: return "Wet fall leaves are slippery. Watch your step.";
                    case 1: return "Rain helps fall mushrooms grow. Check the secret woods.";
                    case 2: return "Cozy rainy fall day! I made spiced cider if you want some.";
                    case 3: return "Remember how we got lost foraging in the fall rain last year?";
                    case 4: return "Let's cuddle under a blanket and watch the fall rain together!";
                }
            }
            else if (weather == Weather.Stormy) {
                switch (friendshipLevel) {
                    case 0: return "Fall storms strip leaves from trees. Annoying.";
                    case 1: return "Stormy fall weather means check your fruit trees for damage.";
                    case 2: return "Spooky fall storms! Perfect for telling fortunes. Want me to read yours?";
                    case 3: return "This reminds me of when we sheltered in the community center during the storm.";
                    case 4: return "Stormy nights mean extra cuddles! I'll protect you from the thunder.";
                }
            }
            else if (weather == Weather.Snowy) {
                switch (friendshipLevel) {
                    case 0: return "Early snow. Winter's coming early.";
                    case 1: return "Snow in fall is unusual. Might want to harvest remaining crops.";
                    case 2: return "First snow of the season! Let's make fall snow angels!";
                    case 3: return "Remember our first kiss during that surprise fall snow?";
                    case 4: return "Early snow means early snuggles! My fireplace is already lit for you.";
                }
            }
        }
        else { // Winter
            if (weather == Weather.Sunny) {
                switch (friendshipLevel) {
                    case 0: return "Winter sun has no warmth. Obviously.";
                    case 1: return "Sunny winter days are rare. Good for ice fishing.";
                    case 2: return "The winter sunlight makes the snow sparkle! Want to go sledding?";
                    case 3: return "This sunshine reminds me of when we built that huge snowman together.";
                    case 4: return "Let's recreate our first winter date! I packed hot chocolate and cookies.";
                }
            }
            else if (weather == Weather.Rainy) {
                switch (friendshipLevel) {
                    case 0: return "Rain in winter? That's just wrong.";
                    case 1: return "Winter rain means icy paths. Be careful.";
                    case 2: return "Weird winter rain! Makes me crave soup. Join me at the saloon?";
                    case 3: return "Remember how we slipped on winter rain ice and fell together?";
                    case 4: return "Icy rain means staying in together! I'll keep you warm all night.";
                }
            }
            else if (weather == Weather.Stormy) {
                switch (friendshipLevel) {
                    case 0: return "Winter storms are deadly. Stay inside.";
                    case 1: return "Blizzard coming. Stock up on firewood.";
                    case 2: return "Perfect weather for telling winter legends by the fire! Interested?";
                    case 3: return "This storm reminds me of when we got snowed in together last year.";
                    case 4: return "Blizzard nights are for sharing blankets! I saved the warmest one for you.";
                }
            }
            else if (weather == Weather.Snowy) {
                switch (friendshipLevel) {
                    case 0: return "Snow. Again. How surprising.";
                    case 1: return "Fresh snow means good crystal fruit harvest next year.";
                    case 2: return "Snowball fight! I've been practicing my throws - ready to lose?";
                    case 3: return "Remember how we built that snow fort last winter? Let's do it again!";
                    case 4: return "First snow means first kiss anniversary! Let's celebrate at our special spot.";
                }
            }
        }
        return "Nice weather we're having."; // Default fallback
    }
}
