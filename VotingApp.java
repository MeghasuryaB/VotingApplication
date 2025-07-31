
import java.util.*;

class Candidate {

    int id;
    String name;
    String party;
    int votes;

    public Candidate(int id, String name, String party) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.votes = 0;
    }

    public void addVote() {
        votes++;
    }

    public void display() {
        System.out.printf("Candidate ID: %d | Name: %s | Party: %s | Votes: %d\n", id, name, party, votes);
    }
}

class Voter {

    String voterId;
    String name;
    int age;
    boolean hasVoted;

    public Voter(String voterId, String name, int age) {
        this.voterId = voterId;
        this.name = name;
        this.age = age;
        this.hasVoted = false;
    }
}

class VotingSystem {

    List<Candidate> candidates = new ArrayList<>();
    Map<String, Voter> voters = new HashMap<>();
    boolean votingOpen = false;

    public VotingSystem() {
        // Predefined parties
        addCandidate("Candidate A", "DMK");
        addCandidate("Candidate B", "TVK");
        addCandidate("Candidate C", "ADMK");
        addCandidate("Candidate D", "NTK");
    }

    public void addCandidate(String name, String party) {
        candidates.add(new Candidate(candidates.size() + 1, name, party));
        System.out.println("Candidate added: " + name + " from " + party);
    }

    public void listVoters() {
        System.out.println("\n--- Registered Voters ---");
        for (Voter v : voters.values()) {
            System.out.println("Voter ID: " + v.voterId + ", Name: " + v.name
                    + ", Age: " + v.age + ", Has Voted: " + v.hasVoted);
        }
    }

    public void registerVoter(String voterId, String name, int age) {
        if (age < 18) {
            System.out.println("Voter must be at least 18 years old to register.");
            return;
        }
        if (voters.containsKey(voterId)) {
            System.out.println("Voter already registered.");
        } else {
            voters.put(voterId, new Voter(voterId, name, age));
            System.out.println("Voter registered successfully: " + name);
        }
    }

    public void openVoting() {
        votingOpen = true;
        System.out.println("Voting has started!");
    }

    public void closeVoting() {
        votingOpen = false;
        System.out.println("Voting has ended.");
    }

    public void castVote(String voterId, int candidateId) {
        if (!votingOpen) {
            System.out.println("Voting is not open.");
            return;
        }
        Voter voter = voters.get(voterId);
        if (voter == null) {
            System.out.println("Voter not registered.");
            return;
        }
        if (voter.hasVoted) {
            System.out.println("Voter has already voted.");
            return;
        }
        if (candidateId < 1 || candidateId > candidates.size()) {
            System.out.println("Invalid candidate.");
            return;
        }
        candidates.get(candidateId - 1).addVote();
        voter.hasVoted = true;
        System.out.println("Vote cast successfully for " + candidates.get(candidateId - 1).name
                + " (" + candidates.get(candidateId - 1).party + ").");
    }

    public void viewResults() {
        System.out.println("\n--- Voting Results ---");
        for (Candidate c : candidates) {
            c.display();
        }
    }

    public void declareWinner() {
        if (candidates.isEmpty()) {
            System.out.println("No candidates available.");
            return;
        }
        int maxVotes = 0;
        for (Candidate c : candidates) {
            if (c.votes > maxVotes) {
                maxVotes = c.votes;
            }
        }
        // collect winners
        List<Candidate> winners = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.votes == maxVotes) {
                winners.add(c);
            }
        }
        System.out.println("\n--- Election Winner ---");
        if (winners.size() == 1) {
            Candidate w = winners.get(0);
            System.out.println("Winner: " + w.name + " (" + w.party + ") with " + w.votes + " votes.");
        } else {
            System.out.println("Tie between:");
            for (Candidate w : winners) {
                System.out.println(w.name + " (" + w.party + ") with " + w.votes + " votes");
            }
        }
    }

    public void viewVoterStatus(String voterId) {
        Voter voter = voters.get(voterId);
        if (voter == null) {
            System.out.println("Voter not registered.");
        } else {
            System.out.println("Voter ID: " + voter.voterId + " | Name: " + voter.name
                    + " | Age: " + voter.age + " | Has Voted: " + voter.hasVoted);
        }
    }
}

public class VotingApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VotingSystem vs = new VotingSystem();

        while (true) {
            System.out.println("\n===== Welcome to Voting System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Voter Login");
            System.out.println("0. Exit");
            System.out.print("Select your role: ");
            int role = sc.nextInt();
            sc.nextLine();

            if (role == 1) {
                System.out.print("Enter admin password: ");
                String pass = sc.nextLine();
                if (!pass.equals("admin123")) {
                    System.out.println("Incorrect password.");
                    continue;
                }
                while (true) {
                    System.out.println("\n--- Admin Menu ---");
                    System.out.println("1. Add Candidate");
                    System.out.println("2. Register Voter");
                    System.out.println("3. Open Voting");
                    System.out.println("4. Close Voting");
                    System.out.println("5. View Results");
                    System.out.println("6. View All Voters");
                    System.out.println("7. Declare Winner");
                    System.out.println("0. Logout");
                    System.out.print("Choose option: ");
                    int ch = sc.nextInt();
                    sc.nextLine();
                    if (ch == 0) {
                        break;
                    }
                    switch (ch) {
                        case 1:
                            System.out.print("Enter Candidate Name: ");
                            String cname = sc.nextLine();
                            System.out.print("Enter Party Name: ");
                            String party = sc.nextLine();
                            vs.addCandidate(cname, party);
                            break;
                        case 2:
                            System.out.print("Enter Voter ID: ");
                            String voterId = sc.nextLine();
                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter Age: ");
                            int age = sc.nextInt();
                            sc.nextLine();
                            vs.registerVoter(voterId, name, age);
                            break;
                        case 3:
                            vs.openVoting();
                            break;
                        case 4:
                            vs.closeVoting();
                            break;
                        case 5:
                            vs.viewResults();
                            break;
                        case 6:
                            vs.listVoters();
                            break;
                        case 7:
                            vs.declareWinner();
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }
                }

            } else if (role == 2) {
                System.out.print("Enter Voter ID: ");
                String voterId = sc.nextLine();
                vs.viewVoterStatus(voterId);

                System.out.println("\n--- Voter Menu ---");
                System.out.println("--- Available Parties & Candidates ---");
                for (Candidate c : vs.candidates) {
                    System.out.printf("ID: %d | Name: %s | Party: %s\n", c.id, c.name, c.party);
                }

                System.out.println("1. Cast Vote");
                System.out.println("0. Back");
                System.out.print("Choose option: ");
                int ch = sc.nextInt();
                sc.nextLine();
                switch (ch) {
                    case 1:
                        System.out.print("Enter Candidate ID to vote: ");
                        int cid = sc.nextInt();
                        sc.nextLine();
                        vs.castVote(voterId, cid);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid option.");
                }

            } else if (role == 0) {
                System.out.println("Thank you for using the Voting System.");
                break;
            } else {
                System.out.println("Invalid selection. Try again.");
            }
        }
    }
}
