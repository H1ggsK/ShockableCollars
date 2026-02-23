# ShockableCollars

> Fabric addon for PlayerCollars that adds a shock enchantment and a linked shock remote.

**ShockableCollars** is an addon mod for **Fabric 1.21.4** that extends **PlayerCollars / Leashable Collars** with a collar-only shock enchantment and a deed-linked remote. It plugs into PlayerCollars ownership data and collar equipment behavior.

## Core Features

### Shock Enchantment
- **3 Levels:** Shock I, II, and III.
- **Collar Only:** Can only be applied to the PlayerCollars collar item.
- **Enchanting Table Support:** Available in the enchanting table pool.

### Shock Remote
- **Craftable Remote:** Craft a base shock remote item.
- **Deed Linking:** Combine the remote with a **stamped deed of ownership** using the same PlayerCollars ownership-transfer recipe flow.
- **Linked Name:** Linked remotes are renamed to `%s's Shock Remote`.
- **Hold-to-Use Feedback:**
  - Plays `remote_on.ogg` when pressed
  - Stays visually "on" while held
  - Plays `remote_off.ogg` on release
- **State Textures:** Uses different item textures while idle vs held down.

### Shocking Behavior
- **Owner-Validated:** Only the recorded owner on the linked remote can use it.
- **Linked Target Lookup:** Uses PlayerCollars deed/ownership component data to find the linked player.
- **Collar Check:** The target must be wearing a valid owned collar.
- **Enchantment Check:** The worn collar must have the Shock enchantment.
- **Damage:** Deals `2 * enchantment level` damage:
  - Shock I = 2 damage
  - Shock II = 4 damage
  - Shock III = 6 damage
- **Ignores Armor:** Uses a custom damage type tagged to bypass armor.
- **Custom Death Message:** `"player didn't listen and got zapped ..."`
- **Zap Sound:** Plays `zap.ogg` at the shocked player's location.

## Integration / Dependencies

ShockableCollars is not standalone.

It integrates with:
- **PlayerCollars / Leashable Collars** (`playercollars`)
  - Reads ownership data from the deed/owner component
  - Uses the PlayerCollars `owner_transfer` recipe type for remote linking
  - Uses the collar item/tag for validation
- **Accessories (Wispforest)**
  - PlayerCollars equips collars through Accessories slots
  - ShockableCollars checks the target's equipped collar through the Accessories capability

## Requirements

### Runtime (Client/Server)
- Minecraft **1.21.4**
- Fabric Loader
- Fabric API
- **PlayerCollars / Leashable Collars** (`playercollars`)
- **Accessories** (required by PlayerCollars / direct dev dependency here)

### Development / Build
- Java **21** (JDK)
- Gradle (or the project wrapper if you add `gradlew`)

## Installation

1. Install Fabric Loader for Minecraft 1.21.4.
2. Put the following mods in your `mods/` folder:
   - `ShockableCollars`
   - `PlayerCollars / Leashable Collars`
   - `Fabric API`
   - `Accessories` (if not already included by your pack)
3. Start the game/server.

## How To Use

### 1. Craft a Shock Remote
Craft the base `Shock Remote`.

### 2. Link It With a Deed
Use a **stamped deed of ownership** (from PlayerCollars) and combine it with the base remote.

This uses PlayerCollars' ownership transfer crafting behavior, so the remote inherits the deed's owner/target link.

### 3. Enchant the Collar
Apply the **Shock** enchantment to the target's collar (up to level III).

### 4. Use the Remote
Right-click and hold the linked remote:
- Remote toggles on visually/sound-wise
- If the linked player is online, wearing the correct collar, and it has Shock, they are zapped
- Releasing the button plays the off sound

## Notes

- The remote only shocks when damage is successfully applied.
- If the linked player is offline, not wearing the collar, or the collar lacks the Shock enchantment, no shock is applied.
- Subtitles are defined for `remote_on`, `remote_off`, and `zap`.

## Building From Source

```bash
git clone <your-repo-url>
cd ShockableCollars
gradle build
```

If you add a Gradle wrapper later:

```bash
./gradlew build
```

## Credits

- **Zap sound** credit: [Sample Focus - Electric Zap](https://samplefocus.com/samples/electric-zap)
- Remote on/off click behavior and visual inspiration: **PlayerCollars clicker**
- **Icon** credit: [Web Fonts](https://www.onlinewebfonts.com/icon/517379) (per provided attribution / CC BY 4.0 notice)

## License

**All Rights Reserved (ARR)**.
