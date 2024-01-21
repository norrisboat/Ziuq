//
//  LiveQuizViewModel.swift
//  iosApp
//
//  Created by Norris Aboagye on 14/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesAsync

class LiveQuizViewModel : shared.LiveQuizViewModel {
    
    @Published var quizUI: QuizUI? = nil
    
    func observeNavigation() {
        DispatchQueue.main.async {
            Task {
                do {
                    try await self.navigation.collect(collector: FlowCollector<LiveQuizScreenNavigation> { navigation in
                        if navigation as? LiveQuizScreenNavigationQuizReady != nil {
                            self.quizUI = (navigation as? LiveQuizScreenNavigationQuizReady)?.quizUI
                        }
                    })
                } catch {
                    print("Failed with error: \(error)")
                }
            }
            
        }
        
    }
    
}
