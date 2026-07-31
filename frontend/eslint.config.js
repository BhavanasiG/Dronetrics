import antfu from "@antfu/eslint-config";
import jestPlugin from "eslint-plugin-jest";
import pluginPinia from "eslint-plugin-pinia";
import testingLibraryPlugin from "eslint-plugin-testing-library";

export default antfu({
  stylistic: {
    indent: 2, // 4, or 'tab'
    quotes: "double", // or 'double'
    semi: true,
    overrides: {
      "style/brace-style": ["error", "1tbs"],
      "style/indent-binary-ops": ["error", 4],
      "style/member-delimiter-style": ["error", {
        multiline: {
          delimiter: "comma",
          requireLast: true,
        },
        singleline: {
          delimiter: "comma",
          requireLast: false,
        },
      }],
    },
  },
  typescript: {
    tsconfigPath: "tsconfig.json",
    overrides: {
      "ts/no-explicit-any": "error",
      "ts/switch-exhaustiveness-check": ["error", {
        considerDefaultExhaustiveForUnions: true,
      }],
      "ts/consistent-type-definitions": "off",
    },
  },
  unicorn: {
    overrides: {
      // Auto fixing this corrupts vue files so it's not worth having on
      "unicorn/prefer-includes": "off",
    },
  },
  e18e: {
    overrides: {
      "e18e/prefer-static-regex": "off",
      "e18e/prefer-array-from-map": "off",
    },
  },
  vue: {
    overrides: {
      "vue/component-name-in-template-casing": "off",
      "vue/block-order": ["error", { order: ["template", "script", "style"] }],
      "vue/v-slot-style": "off",
      "vue/component-options-name-casing": "off",
      "vue/custom-event-name-casing": "off",
      "vue/html-closing-bracket-newline": "off",
      "vue/html-closing-bracket-spacing": "off",
      "vue/first-attribute-linebreak": "off",
      "vue/object-curly-spacing": "off",
    },
  },

}, {
  languageOptions: {
    globals: {
      $: "readonly",
    },
    parserOptions: {
      projectService: true,
      extraFileExtensions: [".vue"],
      tsconfigRootDir: import.meta.dirname,
    },
  },
  plugins: {
    pinia: pluginPinia,
  },
  rules: {
    "pinia/no-store-to-refs-in-store": "error",
    "pinia/prefer-single-store-per-file": "error",
    "pinia/no-duplicate-store-ids": "error",
    "pinia/never-export-initialized-store": "error",
    "pinia/require-setup-store-properties-export": "error",
  },
}, {
  // For *.app.js files we need to have the `core.app.js` import first
  files: ["**/*.app.js"],
  rules: {
    "perfectionist/sort-imports": "off",
  },
}, {
  files: ["**/tests/**/*.[jt]s?(x)", "**/?(*.)+(spec|test).[jt]s?(x)"],
  ...jestPlugin.configs["flat/recommended"],
  ...testingLibraryPlugin.configs["flat/vue"],
  rules: {
    "test/consistent-test-it": "off",
    "test/prefer-lowercase-title": "off",
    "testing-library/prefer-user-event": "error",
  },
});
